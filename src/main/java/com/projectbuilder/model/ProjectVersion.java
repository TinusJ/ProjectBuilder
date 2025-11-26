package com.projectbuilder.model;

/**
 * Represents a project version.
 */
public record ProjectVersion(String version, String displayName) {
    
    public static ProjectVersion[] getAvailableVersions() {
        return new ProjectVersion[]{
            new ProjectVersion("3.2.0", "Spring Boot 3.2.0"),
            new ProjectVersion("3.1.5", "Spring Boot 3.1.5"),
            new ProjectVersion("3.0.12", "Spring Boot 3.0.12"),
            new ProjectVersion("2.7.18", "Spring Boot 2.7.18")
        };
    }
}
