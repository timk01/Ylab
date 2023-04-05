package io.ylab.intensive.lesson05.messagefilter.database;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@Component
public class DataBaseIntegratorImpl implements DataBaseIntegrator {

    private final String[] tables = {"TABLE"};
    private final File initialBadWordsFile = new File("lined_bad_words.txt");
    private static final String BADWORDS_TABLE = "bad_words_table";
    private static final String TABLE_CREATION_QUERY = """
            create table bad_words_table (
            word_id bigserial primary key,
            bad_word varchar)
            """;
    private static final String FIND_THE_BAD_WORD_QUERY = """
            SELECT *
            FROM bad_words_table
            WHERE bad_word = LOWER(?)
            """;
    private static final String CLEAR_TABLE_QUERY = "delete from bad_words_table";
    private static final String INSERT_QUERY = "insert into bad_words_table (bad_word) values (LOWER(?))";

    private final DataSource dataSource;
    private DatabaseMetaData databaseMetaData;

    public DataBaseIntegratorImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void fillDbWithBadWords() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            databaseMetaData = connection.getMetaData();

            if (!isTableExist()) {
                createTable();
            }

            clearDb();

            fillDb(connection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableExist() throws SQLException {
        try (ResultSet resultSet =
                     this.databaseMetaData.getTables(null, null, BADWORDS_TABLE, this.tables)) {

            return resultSet.next();
        }
    }

    private void createTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(TABLE_CREATION_QUERY);
        }
    }

    private void clearDb() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_QUERY)) {
            preparedStatement.executeUpdate();
        }
    }

    private void fillDb(Connection connection) throws IOException, SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(initialBadWordsFile));
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            String line;
            while ((line = reader.readLine()) != null) {
                preparedStatement.setString(1, line);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public boolean isTheWordBad(String aBadWordForSearch) throws SQLException {
        boolean isTheWordBad = false;
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_THE_BAD_WORD_QUERY)) {
            statement.setString(1, aBadWordForSearch);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isTheWordBad = true;
            }
            resultSet.close();
        }
        return isTheWordBad;
    }
}