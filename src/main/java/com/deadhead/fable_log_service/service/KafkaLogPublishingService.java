package com.deadhead.fable_log_service.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaLogPublishingService implements LogPublishingService<Map<String, Object>, Map<String, String>> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, String> publish(Map<String, Object> message) {
        message.put("timestamp", Instant.now().getEpochSecond());
        try {
            kafkaTemplate.send("fable_logs", this.objectMapper.writeValueAsString(message)).thenAccept((ack) -> {
                // log.info(">> message published {}", message);
            }).exceptionally(ex -> {
                log.error(">>>>>>>>>>>{}", ex.getMessage());
                return null;
            });
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
