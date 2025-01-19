package com.deadhead.fable_log_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FableLogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FableLogServiceApplication.class, args);
	}

}
