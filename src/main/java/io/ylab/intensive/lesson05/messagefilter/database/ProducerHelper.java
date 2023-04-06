package io.ylab.intensive.lesson05.messagefilter.database;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProducerHelper {

    private static final String QUEUE_OUTPUT_NAME = "output";
    private static final String EXCHANGE_NAME = "exchange";
    private static final String ROUTING_KEY_NAME = "ylab.output";

    public void produceInitialSettings(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE_OUTPUT_NAME, false, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueBind(QUEUE_OUTPUT_NAME, EXCHANGE_NAME, ROUTING_KEY_NAME);
    }

    public void publishMessage(Channel channel, String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_NAME, null, message.getBytes());
    }
}
