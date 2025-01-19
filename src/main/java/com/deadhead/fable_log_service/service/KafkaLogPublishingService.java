package com.deadhead.fable_log_service.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaLogPublishingService implements LogPublishingService<Map<String, Object>, Map<String, String>> {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final LogMessageStorageServiceI logMessageStorageService;

    @Value("${log-service.topic_name}")
    private String loggingTopic;

    @Override
    public Map<String, String> publish(Map<String, Object> message) {
        message.put("timestamp", Instant.now().getEpochSecond());
        CompletableFuture<SendResult<String, Object>> responseFuture = kafkaTemplate.send(loggingTopic,
                message);
        responseFuture.whenComplete((res, ex) -> {
            if (ex != null) {
                logMessageStorageService.saveMessageToSecondaryStorage(message);
            }
        });

        return null;
    }
}
