package com.example.pulsardemo;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerTest {
    Logger log = LoggerFactory.getLogger("ConsumerTest");

    @Test
    public void consumerDemo() {
        try (PulsarClient client = PulsarClient.builder().serviceUrl("pulsar://172.17.117.29:6650").build()){

            String npTopic = "non-persistent://public/default/my-topic";
            String subscriptionName = "my-subscription";
            String consumerName = "cn-haijiao";
            Consumer consumer = client.newConsumer(Schema.STRING)
                    .topic("key-topic")
                    // .topic(npTopic)
                    .subscriptionName(subscriptionName).consumerName(consumerName)
                    .subscribe();

            while (true) {
                // Wait for a message
                Message msg = consumer.receive();

                try {
                    // Do something with the message
                    log.info("###### Subscription: {} Message received: key:{} value:{} properties: {}", subscriptionName, msg.getKey(), msg.getValue(), msg.getProperties());

                    // Acknowledge the message so that it can be deleted by the message broker
                    consumer.acknowledge(msg.getMessageId());


                    // consumer.negativeAcknowledge(msg.getMessageId());
                } catch (Exception e) {
                    // Message failed to process, redeliver later
                    consumer.negativeAcknowledge(msg);
                }
            }
        } catch (Exception e) {
            log.error("exception: {}", e);
        }
    }
}
