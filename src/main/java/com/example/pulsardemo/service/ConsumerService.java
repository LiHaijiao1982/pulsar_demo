package com.example.pulsardemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConsumerService {
    Logger log = LoggerFactory.getLogger(ConsumerService.class);
    private Map<String, PulsarConsumer> consumerMap = new ConcurrentHashMap<>();

    public void createConsumer(String topic, String subscriptionName, int subscriptionType) {
        if (!consumerMap.containsKey(topic)) {
            PulsarConsumer consumer = new PulsarConsumer(topic, subscriptionName, subscriptionType);

            log.info("create consumer topic: {} , subscription: {}, type: {}", topic, subscriptionName, subscriptionType);
            if (null != consumer) {
                consumerMap.put(topic, consumer);
            }
        }
    }
}
