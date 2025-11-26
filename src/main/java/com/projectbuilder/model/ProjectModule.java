package com.projectbuilder.model;

/**
 * Represents a project module that can be included in the Docker Compose setup.
 */
public record ProjectModule(String id, String displayName, String description) {
    
    public static ProjectModule[] getModulesForVersion(String version) {
        // Different modules based on Spring Boot version
        if (version != null && version.startsWith("3.")) {
            return new ProjectModule[]{
                new ProjectModule("web", "Spring Web", "RESTful web services"),
                new ProjectModule("data-jpa", "Spring Data JPA", "Persistent data with JPA"),
                new ProjectModule("security", "Spring Security", "Authentication and authorization"),
                new ProjectModule("actuator", "Spring Actuator", "Monitoring and management"),
                new ProjectModule("redis", "Spring Data Redis", "Redis caching support"),
                new ProjectModule("kafka", "Spring Kafka", "Apache Kafka messaging")
            };
        } else {
            return new ProjectModule[]{
                new ProjectModule("web", "Spring Web", "RESTful web services"),
                new ProjectModule("data-jpa", "Spring Data JPA", "Persistent data with JPA"),
                new ProjectModule("security", "Spring Security", "Authentication and authorization"),
                new ProjectModule("actuator", "Spring Actuator", "Monitoring and management")
            };
        }
    }
}
