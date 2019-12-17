package com.example.pulsardemo.service;

import com.example.pulsardemo.config.SpringUtils;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PulsarProducer {
    Logger log = LoggerFactory.getLogger(PulsarProducer.class);
    private Producer<String>  producer;
    private String topic;


    public PulsarProducer(String topic) {
        this.topic = topic;
        producer = createStringProducer(topic);
    }

    private Producer<String> createStringProducer(String topic) {
        try {
            return SpringUtils.getBean(PulsarClient.class)
                    .newProducer(Schema.STRING)
                    .topic(topic).create();
        } catch (PulsarClientException e) {
            log.error("fail to create producer topic: {}", topic);
        }
        return null;
    }

    public void sendMessage(String message) {
        try {
            producer.send(message);
            // producer.newMessage().key("key1").value(message).send();
        } catch (PulsarClientException e) {
            log.error("Fail to send {} Message: {}, exception: {}", topic, message, e);
        }
    }

    public void sendMessage(String key, String message) {
        try {
            producer.newMessage().key(key).value(message).send();
        } catch (PulsarClientException e) {
            log.error("Fail to send {} Message: {}, exception: {}", topic, message, e);
        }
    }

    @Override
    protected void finalize() {
        try {
            if (null != producer) {
                producer.close();
            }
        } catch (PulsarClientException e) {
            log.error("Fail to close {} PulsarProducer, exception: {}", topic, e);
        }
    }
}
