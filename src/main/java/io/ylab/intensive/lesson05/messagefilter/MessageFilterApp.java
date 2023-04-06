package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.database.ConsumerHelper;
import io.ylab.intensive.lesson05.messagefilter.database.DataBaseIntegrator;
import io.ylab.intensive.lesson05.messagefilter.database.ProducerHelper;
import io.ylab.intensive.lesson05.messagefilter.database.service.MessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {

    private static final Logger logger = LoggerFactory.getLogger(MessageFilterApp.class);

    public static void main(String[] args) throws SQLException, IOException, TimeoutException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        DataBaseIntegrator dataBaseIntegrator = applicationContext.getBean(DataBaseIntegrator.class);

        ConsumerHelper consumerHelper = applicationContext.getBean(ConsumerHelper.class);
        ProducerHelper producerHelper = applicationContext.getBean(ProducerHelper.class);

        try {
            dataBaseIntegrator.fillDbWithBadWords();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);
        MessageChecker messageChecker = applicationContext.getBean(MessageChecker.class);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            consumerHelper.consumerInitialSettings(channel);

            producerHelper.produceInitialSettings(channel);

            while (!Thread.currentThread().isInterrupted()) {

                GetResponse message = consumerHelper.getQueueMsg(channel);

                if (message != null) {

                    String badWordMsg = new String(message.getBody());

                    String checkedMessage = messageChecker.getCheckedMessage(badWordMsg);

                    producerHelper.publishMessage(channel, checkedMessage);
                }
            }
        } catch (IOException | SQLException | TimeoutException e) {
            logger.info(e.getMessage());
        }
    }
}




