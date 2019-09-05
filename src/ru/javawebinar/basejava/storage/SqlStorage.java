package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeSqlQuery("DELETE FROM resume", PreparedStatement::execute);
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
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "DELETE FROM contact " +
                    " WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ps.executeUpdate();
            }
            insertContacts(resume, connection);
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
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSqlQuery("" +
                "SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid " +
                " WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            setContacts(resume, rs);
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
        return sqlHelper.executeSqlQuery("" +
                "SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid " +
                " ORDER BY full_name, uuid", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            boolean hasNext = rs.next();
            while (hasNext) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                hasNext = setContacts(resume, rs);
                resumes.add(resume);
            }
            return resumes;
        });
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

    private boolean setContacts(Resume resume, ResultSet rs) throws SQLException {
        boolean hasNext = true;
        String uuid = resume.getUuid();
        String contactUuid = rs.getString("resume_uuid");
        if (contactUuid != null) {
            while (hasNext && contactUuid != null && contactUuid.equals(uuid)) {
                String contact = rs.getString("value");
                ContactType contactType = ContactType.valueOf(rs.getString("type"));
                resume.setContact(contactType, contact);
                hasNext = rs.next();
                if (hasNext) {
                    contactUuid = rs.getString("resume_uuid");
                }
            }
        } else {
            hasNext = rs.next();
        }
        return hasNext;
    }
}