package com.projectbuilder.model;

/**
 * Represents a database option.
 */
public record DatabaseOption(String type, String displayName) {
    
    public static DatabaseOption[] getAvailableDatabases() {
        return new DatabaseOption[]{
            new DatabaseOption("postgresql", "PostgreSQL"),
            new DatabaseOption("mysql", "MySQL"),
            new DatabaseOption("mariadb", "MariaDB"),
            new DatabaseOption("mongodb", "MongoDB"),
            new DatabaseOption("redis", "Redis")
        };
    }
}
