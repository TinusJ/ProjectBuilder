package com.projectbuilder.service;

import com.projectbuilder.model.DockerComposeConfig;

/**
 * Service interface for generating Docker Compose files.
 * Allows for different implementations and extensibility.
 */
public interface DockerComposeGenerator {
    
    /**
     * Generates a Docker Compose YAML file based on the provided configuration.
     * 
     * @param config the configuration for the Docker Compose file
     * @return the generated Docker Compose YAML content as a String
     */
    String generateDockerCompose(DockerComposeConfig config);
}
