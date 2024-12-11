package com.eventhub.dti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.eventhub.dti.infrastructure.config.CloudinaryConfig;
import com.eventhub.dti.infrastructure.config.JwtConfigProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({ JwtConfigProperties.class, CloudinaryConfig.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Application is running!");
    }
}
