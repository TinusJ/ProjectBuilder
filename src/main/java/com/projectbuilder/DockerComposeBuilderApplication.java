package com.projectbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Docker Compose Builder application.
 */
@SpringBootApplication
public class DockerComposeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerComposeBuilderApplication.class, args);
    }
}
