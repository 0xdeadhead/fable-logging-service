package com.deadhead.fable_log_service.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Document(collection = "fable_logs")
@Data
@Builder
public class LogMessage {
    @Id
    private String id;
    private Map<String, Object> message;
}
