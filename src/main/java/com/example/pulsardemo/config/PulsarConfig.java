package com.example.pulsardemo.config;


import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {
    Logger log = LoggerFactory.getLogger(PulsarConfig.class);
    @Value("${pulsar.url}")
    private String pulsarUrl;

    @Bean
    public PulsarClient pulsarClient() {
        try {
            PulsarClient client = PulsarClient.builder()
                    .serviceUrl(pulsarUrl)
                    .build();

            log.info("success create pulsar client with: {}", pulsarUrl);
            return client;
        } catch (PulsarClientException e) {
            log.error("fail to build PulsarClient, exception: ", e);
        }

        return null;
    }


}
