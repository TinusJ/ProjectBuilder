package com.projectbuilder.model;

/**
 * Represents a Java version option.
 */
public record JavaVersion(String version, String displayName) {
    
    public static JavaVersion[] getAvailableVersions() {
        return new JavaVersion[]{
            new JavaVersion("21", "Java 21 (LTS)"),
            new JavaVersion("17", "Java 17 (LTS)"),
            new JavaVersion("11", "Java 11 (LTS)"),
            new JavaVersion("8", "Java 8 (Legacy)")
        };
    }
}
