package com.jayant.notificationengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync // delivery must not block ingestion - see config/AsyncConfig
@SpringBootApplication
public class NotificationEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationEngineApplication.class, args);
    }

}
