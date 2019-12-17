package com.example.pulsardemo.controller;

import com.example.pulsardemo.service.ConsumerService;
import com.example.pulsardemo.service.ProducerService;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DemoController {
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ConsumerService consumerService;

    @PostMapping(path = "/producer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createSender(@RequestBody String topic) {
        producerService.createProducer(topic);
    }

    @PostMapping(path = "/producer/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendString(@RequestParam("topic") String topic, @RequestBody String message) {
        producerService.sendMessage(topic, message);
    }

    @DeleteMapping(path = "/producer/finalize", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void destroySender(@RequestParam("topic") String topic) {
        producerService.destroyProducer(topic);
    }

    @PostMapping(path = "/consumer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createConsumer(@RequestParam("topic") String topic,
                               @RequestParam(value = "type") int type,
                               @RequestBody String subscription) {
        consumerService.createConsumer(topic, subscription, type);
    }
}
