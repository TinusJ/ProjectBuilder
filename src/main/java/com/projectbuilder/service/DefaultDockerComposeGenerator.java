package com.projectbuilder.service;

import com.projectbuilder.model.DockerComposeConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of DockerComposeGenerator.
 * Generates Docker Compose files based on the configuration.
 */
@Service
public class DefaultDockerComposeGenerator implements DockerComposeGenerator {

    @Override
    public String generateDockerCompose(DockerComposeConfig config) {
        StringBuilder yaml = new StringBuilder();
        
        yaml.append("version: '3.8'\n\n");
        yaml.append("services:\n");
        
        // Add application service
        addApplicationService(yaml, config);
        
        // Add database service
        if (config.getDatabaseType() != null && !config.getDatabaseType().isEmpty()) {
            addDatabaseService(yaml, config);
        }
        
        // Add volumes section if database has backup
        if (config.getBackupFile() != null && config.getBackupFile().length > 0) {
            addVolumesSection(yaml, config);
        }
        
        return yaml.toString();
    }
    
    private void addApplicationService(StringBuilder yaml, DockerComposeConfig config) {
        yaml.append("  app:\n");
        yaml.append("    image: openjdk:").append(config.getJavaVersion()).append("\n");
        yaml.append("    container_name: spring-app\n");
        yaml.append("    ports:\n");
        yaml.append("      - \"8080:8080\"\n");
        
        if (config.getDatabaseType() != null && !config.getDatabaseType().isEmpty()) {
            yaml.append("    depends_on:\n");
            yaml.append("      - db\n");
        }
        
        // Only add environment section if there are modules or database
        boolean hasModules = config.getProjectModules() != null && !config.getProjectModules().isEmpty();
        boolean hasDatabase = config.getDatabaseType() != null && !config.getDatabaseType().isEmpty();
        
        if (hasModules || hasDatabase) {
            yaml.append("    environment:\n");
            
            // Add project modules as environment variable
            if (hasModules) {
                yaml.append("      PROJECT_MODULES: ").append(String.join(",", config.getProjectModules())).append("\n");
            }
            
            if (hasDatabase) {
                addDatabaseEnvironmentVariables(yaml, config);
            }
        }
        
        yaml.append("    volumes:\n");
        yaml.append("      - ./app:/app\n");
        yaml.append("    working_dir: /app\n");
        yaml.append("    command: java -jar application.jar\n");
        yaml.append("\n");
    }
    
    private void addDatabaseService(StringBuilder yaml, DockerComposeConfig config) {
        String dbType = config.getDatabaseType();
        String dbVersion = config.getDatabaseVersion();
        
        yaml.append("  db:\n");
        yaml.append("    image: ").append(dbType).append(":").append(dbVersion).append("\n");
        yaml.append("    container_name: ").append(dbType).append("-db\n");
        yaml.append("    environment:\n");
        
        switch (dbType) {
            case "postgresql":
                yaml.append("      POSTGRES_DB: mydb\n");
                yaml.append("      POSTGRES_USER: dbuser\n");
                yaml.append("      POSTGRES_PASSWORD: dbpassword\n");
                yaml.append("    ports:\n");
                yaml.append("      - \"5432:5432\"\n");
                break;
            case "mysql":
                yaml.append("      MYSQL_DATABASE: mydb\n");
                yaml.append("      MYSQL_USER: dbuser\n");
                yaml.append("      MYSQL_PASSWORD: dbpassword\n");
                yaml.append("      MYSQL_ROOT_PASSWORD: rootpassword\n");
                yaml.append("    ports:\n");
                yaml.append("      - \"3306:3306\"\n");
                break;
            case "mariadb":
                yaml.append("      MARIADB_DATABASE: mydb\n");
                yaml.append("      MARIADB_USER: dbuser\n");
                yaml.append("      MARIADB_PASSWORD: dbpassword\n");
                yaml.append("      MARIADB_ROOT_PASSWORD: rootpassword\n");
                yaml.append("    ports:\n");
                yaml.append("      - \"3306:3306\"\n");
                break;
            case "mongodb":
                yaml.append("      MONGO_INITDB_DATABASE: mydb\n");
                yaml.append("      MONGO_INITDB_ROOT_USERNAME: dbuser\n");
                yaml.append("      MONGO_INITDB_ROOT_PASSWORD: dbpassword\n");
                yaml.append("    ports:\n");
                yaml.append("      - \"27017:27017\"\n");
                break;
            case "redis":
                yaml.append("      REDIS_PASSWORD: dbpassword\n");
                yaml.append("    ports:\n");
                yaml.append("      - \"6379:6379\"\n");
                break;
        }
        
        yaml.append("    volumes:\n");
        
        // Use proper mount paths based on database type
        if ("mongodb".equals(dbType)) {
            yaml.append("      - db-data:/data/db\n");
        } else if ("redis".equals(dbType)) {
            yaml.append("      - db-data:/data\n");
        } else {
            yaml.append("      - db-data:/var/lib/").append(getDataPath(dbType)).append("\n");
        }
        
        if (config.getBackupFile() != null && config.getBackupFile().length > 0) {
            yaml.append("      - ./backup/").append(config.getBackupFileName()).append(":/docker-entrypoint-initdb.d/backup.sql\n");
        }
        
        yaml.append("\n");
    }
    
    private void addDatabaseEnvironmentVariables(StringBuilder yaml, DockerComposeConfig config) {
        String dbType = config.getDatabaseType();
        
        switch (dbType) {
            case "postgresql":
                yaml.append("      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/mydb\n");
                yaml.append("      SPRING_DATASOURCE_USERNAME: dbuser\n");
                yaml.append("      SPRING_DATASOURCE_PASSWORD: dbpassword\n");
                break;
            case "mysql":
            case "mariadb":
                yaml.append("      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/mydb\n");
                yaml.append("      SPRING_DATASOURCE_USERNAME: dbuser\n");
                yaml.append("      SPRING_DATASOURCE_PASSWORD: dbpassword\n");
                break;
            case "mongodb":
                yaml.append("      SPRING_DATA_MONGODB_URI: mongodb://dbuser:dbpassword@db:27017/mydb\n");
                break;
            case "redis":
                yaml.append("      SPRING_REDIS_HOST: db\n");
                yaml.append("      SPRING_REDIS_PORT: 6379\n");
                yaml.append("      SPRING_REDIS_PASSWORD: dbpassword\n");
                break;
        }
    }
    
    private void addVolumesSection(StringBuilder yaml, DockerComposeConfig config) {
        yaml.append("volumes:\n");
        yaml.append("  db-data:\n");
    }
    
    private String getDataPath(String dbType) {
        return switch (dbType) {
            case "postgresql" -> "postgresql/data";
            case "mysql" -> "mysql";
            case "mariadb" -> "mysql";
            case "mongodb" -> "mongodb";
            case "redis" -> "redis";
            default -> "data";
        };
    }
}
