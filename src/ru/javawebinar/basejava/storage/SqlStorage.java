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
                    "SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "DELETE FROM contact " +
                    "WHERE resume_uuid=?")) {
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
                        " WHERE r.uuid =? ",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        setContacts(resume, rs);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>executeSqlQuery("" +
                        "DELETE FROM resume " +
                        "WHERE uuid=?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement psUuid = connection.prepareStatement("" +
                    "SELECT * FROM resume " +
                    "ORDER BY full_name, uuid")) {
                ResultSet rsUuid = psUuid.executeQuery();
                List<Resume> resumes = new ArrayList<>();
                while (rsUuid.next()) {
                    String uuid = rsUuid.getString("uuid");
                    Resume resume = new Resume(uuid, rsUuid.getString("full_name"));
                    try (PreparedStatement psContacts = connection.prepareStatement("" +
                            "SELECT * FROM contact " +
                            "WHERE resume_uuid=?")) {
                        psContacts.setString(1, uuid);
                        ResultSet rsContacts = psContacts.executeQuery();
                        while (rsContacts.next()) {
                            setContacts(resume, rsContacts);
                        }
                    }
                    resumes.add(resume);
                }
                return resumes;
            }
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeSqlQuery("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setContacts(Resume resume, ResultSet resultSet) throws SQLException {
        String contact = resultSet.getString("value");
        ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
        resume.setContact(contactType, contact);
    }
}