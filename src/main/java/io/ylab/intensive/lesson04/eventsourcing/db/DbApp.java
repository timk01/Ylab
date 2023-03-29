package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class DbApp {
    private static final String QUEUE_NAME = "db_queue";
    private static final String SAVE_MESSAGE_TYPE = "save";
    private static final String DELETE_MESSAGE_TYPE = "delete";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        PersonRepositoryImpl repository = new PersonRepositoryImpl(dataSource);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                Map<String, Object> headers = delivery.getProperties().getHeaders();

                if (headers != null) {
                    String messageType = String.valueOf(headers.get("message-type"));
                    String message = new String(delivery.getBody());
                    if (messageType.equals(SAVE_MESSAGE_TYPE)) {
                        repository.savePerson(mapper.readValue(message, Person.class));
                    }
                    if (messageType.equals(DELETE_MESSAGE_TYPE)) {
                        repository.deletePerson(Long.parseLong(message));
                    }
                }
            };

            while (!Thread.currentThread().isInterrupted()) {
                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
                });
            }
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = """
                drop table if exists person;
                create table if not exists person (
                person_id bigint primary key,
                first_name varchar,
                last_name varchar,
                middle_name varchar)
                """;
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
