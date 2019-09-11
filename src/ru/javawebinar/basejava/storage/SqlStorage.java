package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SkillsSection;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeSqlQuery("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "UPDATE resume " +
                    "   SET full_name=? " +
                    " WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContacts(uuid, connection);
            deleteSections(uuid, connection);
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "INSERT INTO resume (full_name, uuid) " +
                    "VALUES (?, ?)")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                ps.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSqlQuery("" +
                "SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid " +
                "  LEFT JOIN section s " +
                "    ON r.uuid = s.resume_uuid" +
                " WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                setContact(resume, rs);
                setSection(resume, rs);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSqlQuery("" +
                "DELETE FROM resume " +
                " WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumesMap = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM resume " +
                    " ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    resumesMap.put(uuid, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM contact " +
                    " ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumesMap.get(rs.getString("resume_uuid"));
                    setContact(resume, rs);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM section " +
                    " ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumesMap.get(rs.getString("resume_uuid"));
                    setSection(resume, rs);
                }
            }
            return null;
        });
        return new ArrayList<>(resumesMap.values());
    }

    @Override
    public int size() {
        return sqlHelper.executeSqlQuery("" +
                "SELECT COUNT(*) " +
                "  FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void deleteContacts(String uuid, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("" +
                "DELETE FROM contact " +
                " WHERE resume_uuid=?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        }
    }

    private void deleteSections(String uuid, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("" +
                "DELETE FROM section " +
                " WHERE resume_uuid=?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        }
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        Map<ContactType, String> contacts = resume.getContacts();
        if (contacts.size() > 0) {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "INSERT INTO contact (resume_uuid, type, value) " +
                    "VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, entry.getKey().name());
                    ps.setString(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        if (sections.size() > 0) {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "INSERT INTO section (resume_uuid, section_type, section_value) " +
                    "VALUES (?, ?, ?)")) {
                for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                    String type = entry.getKey().name();
                    AbstractSection section = entry.getValue();
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, type);
                    switch (type) {
                        case "OBJECTIVE":
                        case "PERSONAL":
                            ps.setString(3, ((AboutSection) section).getElement());
                            ps.addBatch();
                            break;
                        case "ACHIEVEMENT":
                        case "QUALIFICATIONS":
                            List<String> element = ((SkillsSection) section).getElement();
                            ps.setString(3, String.join("-!-!-!-!-", element));
                            ps.addBatch();
                            break;
//                        case "EXPERIENCE":
//                        case "EDUCATION":
                    }
                }
                ps.executeBatch();
            }
        }
    }

    private void setContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.setContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void setSection(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("section_type");
        if (type != null) {
            switch (type) {
                case "OBJECTIVE":
                case "PERSONAL":
                    resume.setSection(SectionType.valueOf(type), new AboutSection(rs.getString("section_value")));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    String joinedString = rs.getString("section_value");
                    List<String> element = Arrays.asList(joinedString.split("-!-!-!-!-"));
                    resume.setSection(SectionType.valueOf(type), new SkillsSection(element));
                    break;
//                case "EXPERIENCE":
//                case "EDUCATION":
            }
        }
    }
}