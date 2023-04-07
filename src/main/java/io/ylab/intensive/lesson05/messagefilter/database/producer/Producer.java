package io.ylab.intensive.lesson05.messagefilter.database.producer;

import com.rabbitmq.client.Channel;

import java.io.IOException;

public interface Producer {
    void produceInitialSettings(Channel channel) throws IOException;

    void publishMessage(Channel channel, String message) throws IOException;
}
