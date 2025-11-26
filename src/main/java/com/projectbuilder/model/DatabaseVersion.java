package com.projectbuilder.model;

import java.util.Map;

/**
 * Represents database version options for different database types.
 */
public record DatabaseVersion(String version, String displayName) {
    
    private static final Map<String, DatabaseVersion[]> VERSION_MAP = Map.of(
        "postgresql", new DatabaseVersion[]{
            new DatabaseVersion("16", "PostgreSQL 16"),
            new DatabaseVersion("15", "PostgreSQL 15"),
            new DatabaseVersion("14", "PostgreSQL 14"),
            new DatabaseVersion("13", "PostgreSQL 13")
        },
        "mysql", new DatabaseVersion[]{
            new DatabaseVersion("8.2", "MySQL 8.2"),
            new DatabaseVersion("8.0", "MySQL 8.0"),
            new DatabaseVersion("5.7", "MySQL 5.7")
        },
        "mariadb", new DatabaseVersion[]{
            new DatabaseVersion("11.2", "MariaDB 11.2"),
            new DatabaseVersion("10.11", "MariaDB 10.11"),
            new DatabaseVersion("10.6", "MariaDB 10.6")
        },
        "mongodb", new DatabaseVersion[]{
            new DatabaseVersion("7.0", "MongoDB 7.0"),
            new DatabaseVersion("6.0", "MongoDB 6.0"),
            new DatabaseVersion("5.0", "MongoDB 5.0")
        },
        "redis", new DatabaseVersion[]{
            new DatabaseVersion("7.2", "Redis 7.2"),
            new DatabaseVersion("7.0", "Redis 7.0"),
            new DatabaseVersion("6.2", "Redis 6.2")
        }
    );
    
    public static DatabaseVersion[] getVersionsForDatabase(String databaseType) {
        return VERSION_MAP.getOrDefault(databaseType, new DatabaseVersion[0]);
    }
}
