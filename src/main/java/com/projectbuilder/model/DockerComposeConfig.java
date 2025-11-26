package com.projectbuilder.model;

/**
 * Configuration data holder for building Docker Compose files.
 * Uses Builder pattern for clean construction.
 */
public class DockerComposeConfig {
    private final String javaVersion;
    private final String projectVersion;
    private final String projectModule;
    private final String databaseType;
    private final String databaseVersion;
    private final byte[] backupFile;
    private final String backupFileName;

    private DockerComposeConfig(Builder builder) {
        this.javaVersion = builder.javaVersion;
        this.projectVersion = builder.projectVersion;
        this.projectModule = builder.projectModule;
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

    public String getProjectModule() {
        return projectModule;
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
        private String projectModule;
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

        public Builder projectModule(String projectModule) {
            this.projectModule = projectModule;
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
