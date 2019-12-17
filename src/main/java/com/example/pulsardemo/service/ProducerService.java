package com.example.pulsardemo.service;

import org.apache.pulsar.shade.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProducerService {
    Logger log = LoggerFactory.getLogger(ProducerService.class);
    private Map<String, PulsarProducer>  producerMap = new ConcurrentHashMap<>();


    public synchronized void createProducer(String topic) {
        if (!producerMap.containsKey(topic)) {
            PulsarProducer pulsarProducer = new PulsarProducer(topic);

            log.info("create PulsarProducer for topic: {}", topic);
            if (null != pulsarProducer) {
                producerMap.put(topic, pulsarProducer);
            }
        }
    }
    public void sendMessage(String topic, String message) {
        if (StringUtils.isNotBlank(topic)) {
            PulsarProducer producer = producerMap.get(topic);
            if (null != producer) {
                log.info("send message: {} to topic: {}", message, topic);
                producer.sendMessage(message);
            }
        }
    }

    public void destroyProducer(String topic) {
        log.info("destroy PulsarProducer of topic: {}", topic);
        producerMap.remove(topic);
    }
}
