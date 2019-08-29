package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        PreparedStatement ps = sqlHelper.getPrepareStatement("DELETE FROM resume");
        sqlHelper.executeSqlCommand(ps, preparedStatement -> ps.execute());
    }

    @Override
    public void update(Resume resume) {
        PreparedStatement ps = sqlHelper.getPrepareStatement("UPDATE resume SET full_name=? WHERE uuid=?");
        sqlHelper.executeSqlCommand(ps, preparedStatement -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        PreparedStatement ps = sqlHelper.getPrepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?, ?)");
        sqlHelper.executeSqlCommand(ps, preparedStatement -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            try {
                ps.execute();
            } catch (SQLException e) {
                throw new ExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        PreparedStatement ps = sqlHelper.getPrepareStatement("SELECT * FROM resume r WHERE r.uuid = ?");
        return sqlHelper.executeSqlCommand(ps, preparedStatement -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        PreparedStatement ps = sqlHelper.getPrepareStatement("DELETE FROM resume WHERE uuid=?");
        sqlHelper.executeSqlCommand(ps, preparedStatement -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        PreparedStatement ps = sqlHelper.getPrepareStatement("SELECT * FROM resume ORDER BY full_name, uuid");
        return sqlHelper.executeSqlCommand(ps, preparedStatement -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        PreparedStatement ps = sqlHelper.getPrepareStatement("SELECT COUNT(*) FROM resume");
        return sqlHelper.executeSqlCommand(ps, preparedStatement -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}