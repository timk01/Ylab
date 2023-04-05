package io.ylab.intensive.lesson05.messagefilter.database;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private static final String FIND_ALL_BAD_WORDS_SQL = """
            SELECT *
            FROM bad_words_table
            """;
    private static final String CLEAR_TABLE_QUERY = "delete from bad_words_table";
    private static final String INSERT_QUERY = "insert into bad_words_table (bad_word) values (?)";

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
    public List<String> findAllBadWords() throws SQLException {
        List<String> badWordsSting = new ArrayList<>();
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BAD_WORDS_SQL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                badWordsSting.add(resultSet.getString(1));
            }
            return badWordsSting;
        }
    }
}