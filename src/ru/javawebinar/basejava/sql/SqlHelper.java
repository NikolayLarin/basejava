package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;
    private Connection connection;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public PreparedStatement getPrepareStatement(String sql) {
        try {
            connection = connectionFactory.getConnection();
            return connectionFactory.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T executeSqlCommand(PreparedStatement preparedStatement, SqlExecutor<T> sqlExecutor) {
        try {
            T value = sqlExecutor.execute(preparedStatement);
            connection.close();
            return value;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}