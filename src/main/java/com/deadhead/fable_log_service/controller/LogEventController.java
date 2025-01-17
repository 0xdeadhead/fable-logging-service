package com.deadhead.fable_log_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.deadhead.fable_log_service.service.LogPublishingService;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Slf4j
@RequestMapping("/api/log")
public class LogEventController {

    private AtomicLong hitCount;

    @Autowired
    private LogPublishingService<Map<String, Object>, Map<String, String>> logPublishingService;

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Map<String, Object> logEvent) {

        logPublishingService.publish(logEvent);

        return ResponseEntity.ok().body(Map.of("status", "published"));
    }

}
