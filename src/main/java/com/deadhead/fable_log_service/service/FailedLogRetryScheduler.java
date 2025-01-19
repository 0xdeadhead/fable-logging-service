package com.deadhead.fable_log_service.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.deadhead.fable_log_service.repository.LogMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FailedLogRetryScheduler {
    private final LogMessageRepository logMessageRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${log-service.topic_name}")
    private String loggingTopic;

    @Scheduled(fixedRate = 2000)
    public void retrySendingFailedMessages() {
        logMessageRepository.findAll().forEach((logMessage) -> {
            CompletableFuture<SendResult<String, Object>> ackFuture = kafkaTemplate.send(loggingTopic,
                    logMessage.getMessage());
            ackFuture.whenComplete((res, ex) -> {
                if (ex == null) {
                    logMessageRepository.delete(logMessage);
                    log.info("Successfully resent message {}", logMessage.getMessage());
                }
            });
        });

    }
}
