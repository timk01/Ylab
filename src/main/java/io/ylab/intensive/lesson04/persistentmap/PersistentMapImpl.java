package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private String mapName;
    private DataSource dataSource;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.mapName = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        if (mapName == null) {
            return false;
        }
        String containsSqlQuery = "select * from persistent_map where key = ? and map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containsSqlQuery)) {
            preparedStatement.setString(1, key);
            preparedStatement.setString(2, this.mapName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
        }
        return false;
    }

    @Override
    public List<String> getKeys() throws SQLException { //not null values ?
        if (mapName == null) {
            return List.of();
        }
        List<String> keyList = new ArrayList<>();
        String gettingSqlQuery = "select key from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(gettingSqlQuery)) {
            preparedStatement.setString(1, this.mapName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String keyValue = rs.getString(i);
                    keyList.add(keyValue);
                }
            }
            rs.close();
        }
        return keyList;
    }

    @Override
    public String get(String key) throws SQLException {
        if (mapName == null) {
            return null;
        }
        String value = null;
        String gettingSqlQuery = "select value from persistent_map where key = ? and map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(gettingSqlQuery)) {
            preparedStatement.setString(1, key);
            preparedStatement.setString(2, this.mapName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                value = rs.getString(1);
            }
            rs.close();
        }
        return value;
    }

    @Override
    public void remove(String key) throws SQLException {
        if (mapName == null) {
            return;
        }
        String deleteSqlQuery = "delete from persistent_map where key = ? and map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatementForDeletion = connection.prepareStatement(deleteSqlQuery)) {
            preparedStatementForDeletion.setString(1, key);
            preparedStatementForDeletion.setString(2, this.mapName);
            preparedStatementForDeletion.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (mapName == null || key == null) {
            return;
        }
        remove(key);
        String insertQuery = "insert into persistent_map (map_name, key, value) values (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, this.mapName);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        if (mapName == null) {
            return;
        }
        String deleteMapSqlQuery = "delete from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteMapSqlQuery)) {
            preparedStatement.setString(1, this.mapName);
            preparedStatement.executeUpdate();
        }
    }
}
