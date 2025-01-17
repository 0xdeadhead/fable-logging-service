package com.deadhead.fable_log_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@Slf4j
public class BlobStorageService {
    @Autowired
    private S3Client s3Client;
    @Value("${s3.bucketName}")
    private String bucketName;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        this.objectMapper = new ObjectMapper();
        log.info(">>bucketName {}", bucketName);
    }

    public void logDataToBucket(List<String> data, String key)
            throws S3Exception, AwsServiceException, SdkClientException, JsonProcessingException {
        log.info(">>bucketName {}", bucketName);

        PutObjectRequest objectPutRequest = PutObjectRequest.builder()
                .bucket(this.bucketName)
                .key(key)
                .contentType("application/json")
                .build();
        s3Client.putObject(objectPutRequest, RequestBody.fromString(this.objectMapper.writeValueAsString(data)));

    }

}
