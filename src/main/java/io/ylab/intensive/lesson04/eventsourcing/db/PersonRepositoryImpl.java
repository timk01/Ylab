package io.ylab.intensive.lesson04.eventsourcing.db;

import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl {
    private final DataSource dataSource;
    private static final String CREATE_PERSON_SQL = """
            INSERT INTO person
            (person_id, first_name, last_name, middle_name)
            VALUES (?, ?, ?, ?)
            """;
    private static final String UPDATE_PERSON_SQL = """
            UPDATE person
            SET first_name = ?, last_name = ?, middle_name = ?
            WHERE person_id = ?
            """;
    private static final String SELECT_PERSON_BY_ID_SQL = """
            SELECT *
            FROM person
            WHERE person_id = ?
            """;
    private static final String DELETE_PERSON_BY_ID_SQL = """
            DELETE FROM person
            WHERE person_id = ?
            """;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void savePerson(Person person) {
        if (!executeQuery(SELECT_PERSON_BY_ID_SQL, person.getId()).isEmpty()) {
            updatePerson(person);
        } else {
            createPerson(person);
        }
    }

    private void createPerson(Person person) {
        executeUpdate(CREATE_PERSON_SQL,
                person.getId(), person.getName(), person.getLastName(), person.getMiddleName());
        System.out.println("Было произведено сохранение нового Person");
    }


    private void updatePerson(Person person) {
        executeUpdate(UPDATE_PERSON_SQL,
                person.getName(), person.getLastName(), person.getMiddleName(), person.getId());
        System.out.println("Было произведено обновление существующего Person");
    }

    public void deletePerson(Long personId) {
        executeUpdate(DELETE_PERSON_BY_ID_SQL, personId);
    }

    private void executeUpdate(String sql, Object... params) {
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            addParametersToStatement(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Person> executeQuery(String sql, Object... params) {
        List<Person> personList = new ArrayList<>();
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            addParametersToStatement(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    personList.add(getPersonFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return personList;
    }

    private Person getPersonFromResultSet(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name")
        );
    }

    private void addParametersToStatement(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Long) {
                statement.setLong(i + 1, (Long) params[i]);
            } else {
                statement.setString(i + 1, String.valueOf(params[i]));
            }
        }
    }
}