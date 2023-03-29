package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;
    private List<Long> longList = new ArrayList<>();

    private static final String SQL_DESCENDING_ORDER_QUERY = "SELECT val FROM numbers ORDER BY val DESC";
    private static final String SQL_NUMBERS_INSERTION_QUERY = "INSERT INTO numbers(val) values (?)";
    private static final String SQL_DELETE_MAP_QUERY = "delete from numbers";

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        File file = new File("new" + data);
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {

            fillListFromFile(reader);

            fillDbFromListWithBatch(this.longList);

            sortDataAndWriteToDisk(file);

            clearDb();
        } catch (IOException | SQLException msg) {
            System.err.println("Exception during sort " + msg);
        }

        return file;
    }

    public File sortWithoutBatch(File data) {
        File file = new File("new" + data);
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {

            fillListFromFile(reader);

            fillDbFromListWithSoloAdding(this.longList);

            sortDataAndWriteToDisk(file);

            clearDb();
        } catch (IOException | SQLException msg) {
            System.err.println("Exception during sort " + msg);
        }

        return file;
    }

    private void clearDb() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(SQL_DELETE_MAP_QUERY);
        }
    }

    private void fillListFromFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            this.longList.add(Long.parseLong(line));
        }
    }

    private void fillDbFromListWithBatch(List<Long> longs) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_NUMBERS_INSERTION_QUERY)) {

            for (Long longValue : longs) {
                statement.setLong(1, longValue);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void fillDbFromListWithSoloAdding(List<Long> longs) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_NUMBERS_INSERTION_QUERY)) {

            for (Long longValue : longs) {
                statement.setLong(1, longValue);
                statement.executeUpdate();
            }
        }
    }

    private void sortDataAndWriteToDisk(File file) throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL_DESCENDING_ORDER_QUERY);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                while (resultSet.next()) {
                    long aLong = resultSet.getLong(1);
                    writer.write(aLong + "\n");
                }
                resultSet.close();
            }
        }
    }
}

