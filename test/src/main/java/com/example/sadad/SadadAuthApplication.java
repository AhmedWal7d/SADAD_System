package com.example.sadad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.sadad.repository")
@EntityScan(basePackages = "com.example.sadad.entity")

public class SadadAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SadadAuthApplication.class, args);
    }
}
