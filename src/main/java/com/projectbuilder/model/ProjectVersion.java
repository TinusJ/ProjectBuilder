package com.projectbuilder.model;

/**
 * Represents a custom project version.
 */
public record ProjectVersion(String version, String displayName) {
    
    public static ProjectVersion[] getAvailableVersions() {
        return new ProjectVersion[]{
            new ProjectVersion("3.2.0", "Custom Project 3.2.0"),
            new ProjectVersion("3.1.5", "Custom Project 3.1.5"),
            new ProjectVersion("3.0.12", "Custom Project 3.0.12"),
            new ProjectVersion("2.7.18", "Custom Project 2.7.18")
        };
    }
}
