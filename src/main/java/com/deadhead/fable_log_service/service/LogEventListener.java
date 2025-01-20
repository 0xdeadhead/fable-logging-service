package com.deadhead.fable_log_service.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@Slf4j
public class LogEventListener {

    @Autowired
    private BlobStorageService blobStorageService;

    @KafkaListener(topicPattern = "fable_logs", groupId = "fable-logs-0", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<Map<String, Object>> message, Acknowledgment acknowledgment) {
        log.info("Received message batch of size : {}", message.size());
        try {
            this.blobStorageService.logDataToBucket(message,
                    "log-" + Instant.now().getEpochSecond() + "-" + message.hashCode() + ".json");
            acknowledgment.acknowledge();

        } catch (S3Exception e) {
            e.printStackTrace();
            throw e;
        } catch (AwsServiceException e) {
            e.printStackTrace();
            throw e;
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw e;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
