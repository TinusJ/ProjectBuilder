# Docker Compose Builder

A Java 17+ Spring Boot Vaadin wizard application that generates fully functional Docker Compose files.

## Features

- **5-Step Wizard Interface** for easy Docker Compose generation
  - Step 1: Select Java and Spring Boot versions
  - Step 2: Choose project modules based on version
  - Step 3: Configure database with version selection and optional backup upload
  - Step 4: Review configuration
  - Step 5: View and download generated Docker Compose file

- **Extensible Architecture** following Java best practices
  - Service-oriented design with interface-based contracts
  - Builder pattern for configuration
  - Strategy pattern for Docker Compose generation
  - Clean separation of concerns (Model-View-Service layers)

- **Multiple Database Support**
  - PostgreSQL
  - MySQL
  - MariaDB
  - MongoDB
  - Redis

## Requirements

- Java 17 or higher
- Maven 3.8+

## Building and Running

### Build the application:
```bash
mvn clean install
```

### Run the application:
```bash
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## Usage

1. Open your browser and navigate to `http://localhost:8080`
2. Follow the 5-step wizard to configure your Docker Compose setup:
   - Select Java and Spring Boot versions
   - Choose a project module
   - Configure your database settings
   - Review your configuration
   - Download the generated docker-compose.yml file

## Architecture

### Packages

- `com.projectbuilder.model` - Domain models and records
- `com.projectbuilder.service` - Business logic and Docker Compose generation
- `com.projectbuilder.view` - Vaadin UI components and wizard pages

### Key Design Patterns

- **Builder Pattern**: Used in `DockerComposeConfig` for clean object construction
- **Strategy Pattern**: `DockerComposeGenerator` interface allows different generation strategies
- **Template Method**: `WizardPage` interface defines the wizard flow contract

### Extensibility

The application is designed to be easily extensible:

1. **Adding New Database Types**: 
   - Add new database options in `DatabaseOption`
   - Add versions in `DatabaseVersion`
   - Update generation logic in `DefaultDockerComposeGenerator`

2. **Adding New Project Modules**:
   - Extend `ProjectModule.getModulesForVersion()`

3. **Custom Docker Compose Generation**:
   - Implement the `DockerComposeGenerator` interface
   - Register as a Spring Bean

## License

This project is open source.
