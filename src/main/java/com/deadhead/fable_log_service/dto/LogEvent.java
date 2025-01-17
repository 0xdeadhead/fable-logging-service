package com.deadhead.fable_log_service.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent {
    private Long timeStamp;
    private Map<String, Object> data;
}
