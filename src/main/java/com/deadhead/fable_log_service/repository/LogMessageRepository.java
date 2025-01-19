package com.deadhead.fable_log_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deadhead.fable_log_service.entity.LogMessage;


@Repository
public interface LogMessageRepository extends MongoRepository<LogMessage,Long> {
    
}