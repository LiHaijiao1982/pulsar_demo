package com.example.pulsardemo;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerTest {
    Logger log = LoggerFactory.getLogger("ProducerTest");
    @Test
    public void demo() {
        try (PulsarClient client = PulsarClient.builder().serviceUrl("pulsar://pulsar.example.com:6650").build()){

            String npTopic = "non-persistent://public/default/my-topic";

            Producer<String> producer = client.newProducer(Schema.STRING)
                    .topic("key-topic")
                    // .topic(npTopic)
                    .enableBatching(false)
                    .create();


            producer.send("##### message-" + System.currentTimeMillis());
            producer.newMessage().key("key-1").value("##### message-1-1").property("name", "haijiao").send();

            producer.newMessage().key("key-1").value("##### message-1-2").property("name", "haijiao2").send();

        } catch (Exception e) {
            log.error("Exception: {}", e);
        }
    }
}
