package com.deadhead.fable_log_service.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deadhead.fable_log_service.entity.LogMessage;
import com.deadhead.fable_log_service.repository.LogMessageRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Data
public class LogMessageStorageServiceMongo implements LogMessageStorageServiceI {

    @Autowired
    private final LogMessageRepository logMessageRepository;

    @Override
    public void saveMessageToSecondaryStorage(Map<String, Object> message) {
        logMessageRepository.save(LogMessage.builder().message(message).id(UUID.randomUUID().toString()).build());
    }

  

}
