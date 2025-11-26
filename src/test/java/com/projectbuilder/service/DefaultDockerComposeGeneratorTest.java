package com.projectbuilder.service;

import com.projectbuilder.model.DockerComposeConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDockerComposeGeneratorTest {

    private final DockerComposeGenerator generator = new DefaultDockerComposeGenerator();

    @Test
    void testGenerateDockerComposeWithPostgres() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModule("web")
                .databaseType("postgresql")
                .databaseVersion("16")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("version: '3.8'"));
        assertTrue(result.contains("openjdk:17"));
        assertTrue(result.contains("postgresql:16"));
        assertTrue(result.contains("POSTGRES_DB"));
        assertTrue(result.contains("POSTGRES_USER"));
        assertTrue(result.contains("POSTGRES_PASSWORD"));
    }

    @Test
    void testGenerateDockerComposeWithMySQL() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModule("data-jpa")
                .databaseType("mysql")
                .databaseVersion("8.0")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("mysql:8.0"));
        assertTrue(result.contains("MYSQL_DATABASE"));
        assertTrue(result.contains("MYSQL_USER"));
        assertTrue(result.contains("MYSQL_PASSWORD"));
    }

    @Test
    void testGenerateDockerComposeWithMongoDB() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModule("web")
                .databaseType("mongodb")
                .databaseVersion("7.0")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("mongodb:7.0"));
        assertTrue(result.contains("MONGO_INITDB_DATABASE"));
    }

    @Test
    void testGenerateDockerComposeWithBackupFile() {
        byte[] backupData = "test backup".getBytes();
        
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModule("web")
                .databaseType("postgresql")
                .databaseVersion("16")
                .backupFile(backupData)
                .backupFileName("backup.sql")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("backup.sql"));
        assertTrue(result.contains("volumes:"));
    }
}
