package com.projectbuilder.model;

import java.util.List;

/**
 * Configuration data holder for building Docker Compose files.
 * Uses Builder pattern for clean construction.
 */
public class DockerComposeConfig {
    private final String javaVersion;
    private final String projectVersion;
    private final List<String> projectModules;
    private final String databaseType;
    private final String databaseVersion;
    private final byte[] backupFile;
    private final String backupFileName;

    private DockerComposeConfig(Builder builder) {
        this.javaVersion = builder.javaVersion;
        this.projectVersion = builder.projectVersion;
        this.projectModules = builder.projectModules;
        this.databaseType = builder.databaseType;
        this.databaseVersion = builder.databaseVersion;
        this.backupFile = builder.backupFile;
        this.backupFileName = builder.backupFileName;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public List<String> getProjectModules() {
        return projectModules;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public byte[] getBackupFile() {
        return backupFile;
    }

    public String getBackupFileName() {
        return backupFileName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String javaVersion;
        private String projectVersion;
        private List<String> projectModules;
        private String databaseType;
        private String databaseVersion;
        private byte[] backupFile;
        private String backupFileName;

        public Builder javaVersion(String javaVersion) {
            this.javaVersion = javaVersion;
            return this;
        }

        public Builder projectVersion(String projectVersion) {
            this.projectVersion = projectVersion;
            return this;
        }

        public Builder projectModules(List<String> projectModules) {
            this.projectModules = projectModules;
            return this;
        }

        public Builder databaseType(String databaseType) {
            this.databaseType = databaseType;
            return this;
        }

        public Builder databaseVersion(String databaseVersion) {
            this.databaseVersion = databaseVersion;
            return this;
        }

        public Builder backupFile(byte[] backupFile) {
            this.backupFile = backupFile;
            return this;
        }

        public Builder backupFileName(String backupFileName) {
            this.backupFileName = backupFileName;
            return this;
        }

        public DockerComposeConfig build() {
            return new DockerComposeConfig(this);
        }
    }
}
