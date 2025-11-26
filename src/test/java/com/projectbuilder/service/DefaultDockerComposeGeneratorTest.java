package com.projectbuilder.service;

import com.projectbuilder.model.DockerComposeConfig;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDockerComposeGeneratorTest {

    private final DockerComposeGenerator generator = new DefaultDockerComposeGenerator();

    @Test
    void testGenerateDockerComposeWithPostgres() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModules(List.of("web"))
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
        assertTrue(result.contains("PROJECT_MODULES: web"));
    }

    @Test
    void testGenerateDockerComposeWithMySQL() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModules(List.of("data-jpa"))
                .databaseType("mysql")
                .databaseVersion("8.0")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("mysql:8.0"));
        assertTrue(result.contains("MYSQL_DATABASE"));
        assertTrue(result.contains("MYSQL_USER"));
        assertTrue(result.contains("MYSQL_PASSWORD"));
        assertTrue(result.contains("PROJECT_MODULES: data-jpa"));
    }

    @Test
    void testGenerateDockerComposeWithMongoDB() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModules(List.of("web"))
                .databaseType("mongodb")
                .databaseVersion("7.0")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("mongodb:7.0"));
        assertTrue(result.contains("MONGO_INITDB_DATABASE"));
        assertTrue(result.contains("PROJECT_MODULES: web"));
    }

    @Test
    void testGenerateDockerComposeWithBackupFile() {
        byte[] backupData = "test backup".getBytes();
        
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModules(List.of("web"))
                .databaseType("postgresql")
                .databaseVersion("16")
                .backupFile(backupData)
                .backupFileName("backup.sql")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("backup.sql"));
        assertTrue(result.contains("volumes:"));
        assertTrue(result.contains("PROJECT_MODULES: web"));
    }
    
    @Test
    void testGenerateDockerComposeWithMultipleModules() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModules(List.of("web", "data-jpa", "security"))
                .databaseType("postgresql")
                .databaseVersion("16")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertTrue(result.contains("PROJECT_MODULES: web,data-jpa,security"));
        assertTrue(result.contains("openjdk:17"));
        assertTrue(result.contains("postgresql:16"));
    }
    
    @Test
    void testGenerateDockerComposeWithNoModules() {
        DockerComposeConfig config = DockerComposeConfig.builder()
                .javaVersion("17")
                .projectVersion("3.2.0")
                .projectModules(List.of())
                .databaseType("postgresql")
                .databaseVersion("16")
                .build();

        String result = generator.generateDockerCompose(config);

        assertNotNull(result);
        assertFalse(result.contains("PROJECT_MODULES:"));
        assertTrue(result.contains("openjdk:17"));
    }
}
