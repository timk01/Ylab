package io.ylab.intensive.lesson05.messagefilter.database.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;

public interface Consumer {
    void consumerInitialSettings(Channel channel) throws IOException;

    GetResponse getQueueMsg(Channel channel) throws IOException;
}
