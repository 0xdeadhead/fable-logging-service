package com.deadhead.fable_log_service.service;

import java.util.Map;

public interface LogMessageStorageServiceI {
    public void saveMessageToSecondaryStorage(Map<String, Object> message);

}
