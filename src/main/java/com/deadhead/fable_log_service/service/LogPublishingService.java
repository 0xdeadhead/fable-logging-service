package com.deadhead.fable_log_service.service;

public interface LogPublishingService<M, R> {
    public R publish(M message);
}
