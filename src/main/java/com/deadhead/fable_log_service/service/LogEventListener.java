package com.deadhead.fable_log_service.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topicPattern = "fable_logs", groupId = "fable-logs-0")
    public void listen(List<String> message) {
        log.info("Received messages: {}", message);
        try {
            this.blobStorageService.logDataToBucket(message,
                    "log-" + Instant.now().getEpochSecond() + "-" + message.hashCode() + ".json");
        } catch (S3Exception e) {
            e.printStackTrace();
        } catch (AwsServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SdkClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
