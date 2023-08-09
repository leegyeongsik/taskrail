package com.taskrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing

@SpringBootApplication
public class TaskRailApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskRailApplication.class, args);
    }

}