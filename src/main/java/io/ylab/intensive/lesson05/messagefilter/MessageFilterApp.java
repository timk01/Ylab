package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.messagefilter.database.DataBaseIntegrator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
    private static final String QUEUE_OUTPUT_NAME = "output";
    private static final String QUEUE_INPUT_NAME = "input";
    private static final String EXCHANGE_NAME = "exchange";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        DataBaseIntegrator dataBaseIntegrator = applicationContext.getBean(DataBaseIntegrator.class);


        try {
            dataBaseIntegrator.fillDbWithBadWords();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_INPUT_NAME, true);
                if (message != null) {
                    String badWordMsg = new String(message.getBody());

                    StringBuilder sbCensoredString = new StringBuilder();
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sbNew = new StringBuilder();

                    appendAllWordsButLast(dataBaseIntegrator, badWordMsg, sbCensoredString, sb, sbNew);

                    appendLastWord(dataBaseIntegrator, badWordMsg, sbCensoredString);

                    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
                    channel.queueDeclare(QUEUE_OUTPUT_NAME, false, false, false, null);
                    channel.queueBind(QUEUE_OUTPUT_NAME, EXCHANGE_NAME, "*");

                    channel.basicPublish(EXCHANGE_NAME, "*", null, sbCensoredString.toString().getBytes());
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        } catch (TimeoutException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void appendAllWordsButLast(DataBaseIntegrator dataBaseIntegrator, String badWordMsg,
                                              StringBuilder sbCensoredString,
                                              StringBuilder sb, StringBuilder sbNew) throws SQLException {
        char[] chars = badWordMsg.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar == ' ' || aChar == '.' || aChar == ','
                    || aChar == ';' || aChar == '?' || aChar == '!' || aChar == '\n') {
                if (dataBaseIntegrator.isTheWordBad(sb.toString())) {
                    sbNew.append(replaceAll(sb.toString(), '*'));
                    if (!sbNew.isEmpty()) {
                        sbCensoredString.append(sbNew);
                        sb.setLength(0);
                        sbNew.setLength(0);
                    }
                } else {
                    sbCensoredString.append(sb);
                    sb.setLength(0);
                }
                sbCensoredString.append(aChar);
            } else {
                sb.append(aChar);
            }
        }
    }

    private static void appendLastWord(DataBaseIntegrator dataBaseIntegrator,
                                       String badWordMsg, StringBuilder sbCensoredString) throws SQLException {
        String stringSplitter = "[ +.+,+;+?+!+\n+]";

        String[] splittedString = badWordMsg.split(stringSplitter);

        String s = splittedString[splittedString.length - 1];
        if (!dataBaseIntegrator.isTheWordBad(s)) {
            sbCensoredString.append(s);
        } else {
            sbCensoredString.append(replaceAll(s, '*'));
        }
    }

    private static String replaceAll(String word, char replacer) {
        StringBuilder ret = new StringBuilder();

        if (word.length() > 2) {
            ret.append(word.charAt(0));
            for (int i = 1; i < word.length() - 1; i++) {
                ret.append(replacer);
            }
            ret.append(word.charAt(word.length() - 1));
            return ret.toString();
        }

        return word;
    }
}


