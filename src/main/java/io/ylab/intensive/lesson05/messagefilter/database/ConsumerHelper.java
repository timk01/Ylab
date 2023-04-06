package io.ylab.intensive.lesson05.messagefilter.database;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsumerHelper {
    private static final String QUEUE_INPUT_NAME = "input";
    private static final String EXCHANGE_NAME = "exchange";
    private static final String ROUTING_NAME = "ylab.input";

    public void consumerInitialSettings(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE_INPUT_NAME, false, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueBind(QUEUE_INPUT_NAME, EXCHANGE_NAME, ROUTING_NAME);
    }

    public GetResponse getQueueMsg(Channel channel) throws IOException {
        return channel.basicGet(QUEUE_INPUT_NAME, true);
    }
}
