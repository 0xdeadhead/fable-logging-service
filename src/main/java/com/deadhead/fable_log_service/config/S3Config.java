package com.deadhead.fable_log_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${s3.access_key_id}")
    private String accessKey;
    @Value("${s3.access_key_secret}")
    private String accessSecret;

    @Bean
    public S3Client s3Client() {
        S3Client s3 = S3Client.builder().region(Region.AP_SOUTH_2)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, accessSecret)))
                .build();
        return s3;
    }
}
