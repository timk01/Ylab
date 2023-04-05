package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;


/**
 * Тут пишем реализацию
 */
@Component
public class PersonApiImpl implements PersonApi {
    private final DataSource dataSource;
    private final ConnectionFactory connectionFactory;
    private static final String QUEUE_NAME = "db_queue";
    private static final String SAVE_MESSAGE_TYPE = "save";
    private static final String DELETE_MESSAGE_TYPE = "delete";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String FIND_ALL_PERSON_SQL = """
            SELECT *
            FROM person
            """;
    private static final String FIND_PERSON_BY_ID_SQL = """
            SELECT *
            FROM person
            WHERE person_id = ?
            """;

    public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) {
        try {
            sendMessage(DELETE_MESSAGE_TYPE, String.valueOf(personId).getBytes());
        } catch (IOException | TimeoutException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try {
            sendMessage(SAVE_MESSAGE_TYPE, getJsonFromPerson(new Person(
                    personId,
                    firstName,
                    lastName,
                    middleName
            )));
        } catch (IOException | TimeoutException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public Person findPerson(Long personId) {
        List<Person> personList = executeQuery(FIND_PERSON_BY_ID_SQL, personId);
        if (personList.isEmpty()) {
            return null;
        }
        return personList.get(0);
    }

    @Override
    public List<Person> findAll() {
        return executeQuery(FIND_ALL_PERSON_SQL);
    }

    private List<Person> executeQuery(String sql, long... params) {
        List<Person> personList = new ArrayList<>();
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setLong(i + 1, params[i]);
            }
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

    private AMQP.BasicProperties getMessageTypeProperties(String messageType) {
        return new AMQP.BasicProperties().builder()
                .headers(Map.of("message-type", messageType))
                .build();
    }

    private void sendMessage(String messageType, byte[] message) throws IOException, TimeoutException {
        try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME, getMessageTypeProperties(messageType), message);
        }
    }

    private byte[] getJsonFromPerson(Person person) throws JsonProcessingException {
        return mapper.writeValueAsBytes(person);
    }
}
