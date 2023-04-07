package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DbApp {
    private static final String QUEUE_NAME = "db_queue";
    private static final String SAVE_MESSAGE_TYPE = "save";
    private static final String DELETE_MESSAGE_TYPE = "delete";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(DbApp.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        PersonRepository personRepository = applicationContext.getBean(PersonRepository.class);
        ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                Map<String, Object> headers = delivery.getProperties().getHeaders();

                if (headers != null) {
                    String messageType = String.valueOf(headers.get("message-type"));
                    String message = new String(delivery.getBody());
                    if (messageType.equals(SAVE_MESSAGE_TYPE)) {
                        personRepository.savePerson(mapper.readValue(message, Person.class));
                    }
                    if (messageType.equals(DELETE_MESSAGE_TYPE)) {
                        personRepository.deletePerson(Long.parseLong(message));
                    }
                }
            };

            while (!Thread.currentThread().isInterrupted()) {
                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
                });
            }
        } catch (IOException | TimeoutException e) {
            logger.info(e.getMessage());
        }
    }
}
