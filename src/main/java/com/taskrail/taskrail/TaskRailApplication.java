package com.taskrail.taskrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TaskRailApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskRailApplication.class, args);
    }

}
