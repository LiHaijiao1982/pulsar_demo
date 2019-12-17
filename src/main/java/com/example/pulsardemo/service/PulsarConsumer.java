package com.example.pulsardemo.service;

import com.example.pulsardemo.config.SpringUtils;
import lombok.Setter;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class PulsarConsumer implements Runnable{
    private static Logger log = LoggerFactory.getLogger(PulsarConsumer.class);

    private Consumer<String> consumer;
    @Setter
    private volatile boolean running = true;

    private String topic;
    private String subscription;
    private SubscriptionType type;

    public PulsarConsumer(String topic, String subscription, int subscriptionType) {
        this.topic = topic;
        this.subscription = subscription;

        SubscriptionType type = SubscriptionType.Exclusive;
        if (!StringUtils.isEmpty(subscriptionType)) {
            if (subscriptionType == SubscriptionType.Failover.ordinal()) {
                type = SubscriptionType.Failover;
            } else if (subscriptionType == SubscriptionType.Shared.ordinal()) {
                type = SubscriptionType.Shared;
            } else if (subscriptionType == SubscriptionType.Key_Shared.ordinal()) {
                type = SubscriptionType.Key_Shared;
            }
        }
        this.type = type;

        consumer = createConsumer(topic, subscription, type);
    }

    private Consumer<String> createConsumer(String topic, String subscription, SubscriptionType subscriptionType) {
        try {
            return SpringUtils.getBean(PulsarClient.class).newConsumer(Schema.STRING)
                    .topic(topic)
                    .subscriptionName(subscription)
                    // .readCompacted(true)     // 是否开启topic compaction
                    .subscriptionType(subscriptionType)
                    .messageListener(new MessageListener<String>() {
                        @Override
                        public void received(Consumer<String> consumer, Message<String> msg) {
                            log.info("receive message: {} for topic: {} subscription: {}",
                                    topic, msg.getValue(), subscription);
                        }
                    }).subscribe();
        } catch (PulsarClientException e) {
            log.error("fail to create consumer topic: {} subscription: {}", topic, subscription);
        }
        return null;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = consumer.receive();
                log.info("receive: {}", message);

                consumer.acknowledge(message);
            } catch (PulsarClientException e) {

            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
