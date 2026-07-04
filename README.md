# Java SonarQube Jenkins Pipeline Project

A comprehensive Java project configured with Jenkins CI/CD pipeline, SonarQube code quality analysis, and JaCoCo code coverage reporting.

## Project Structure

```
├── Jenkinsfile                          # Jenkins pipeline configuration
├── pom.xml                              # Maven project configuration
├── sonar-project.properties             # SonarQube project configuration
├── .gitignore                           # Git ignore rules
├── README.md                            # This file
└── src/
    ├── main/java/com/example/
    │   ├── Application.java             # Main application entry point
    │   └── Utility.java                 # Utility helper class
    └── test/java/com/example/
        ├── ApplicationTest.java         # Application unit tests
        └── UtilityTest.java             # Utility unit tests
```

## Prerequisites

- **Java 11+**
- **Maven 3.6+**
- **Jenkins 2.x+**
- **SonarQube Server** (optional, for code quality analysis)
- **Git**

## Build & Run Locally

### Build the project:
```bash
mvn clean compile
```

### Run unit tests:
```bash
mvn test
```

### Generate code coverage report:
```bash
mvn jacoco:report
```

### Package the application:
```bash
mvn package
```

### Run the application:
```bash
java -jar target/java-jenkins-app-1.0.0.jar
```

## Jenkins Pipeline

The `Jenkinsfile` defines a complete CI/CD pipeline with the following stages:

1. **Checkout** - Clones the repository
2. **Build** - Compiles the Java source code
3. **Unit Tests** - Runs JUnit tests
4. **Code Coverage** - Generates JaCoCo code coverage reports
5. **SonarQube Analysis** - Performs static code analysis
6. **Quality Gate** - Waits for SonarQube quality gate results
7. **Package** - Creates JAR file
8. **Archive Artifacts** - Stores build outputs
9. **Publish Reports** - Publishes test and coverage reports
10. **Deploy** - Deploys the application (on main branch)

### Pipeline Configuration

**Parameters:**
- `SONAR_HOST_URL`: SonarQube server URL (default: http://sonarqube:9000)
- `SONAR_LOGIN`: SonarQube authentication token

**Environment Variables:**
- `JAVA_HOME`: Java installation path
- `MAVEN_HOME`: Maven installation path
- `SONAR_LOGIN`: Retrieved from Jenkins credentials

**Triggers:**
- GitHub push events
- Poll SCM every 15 minutes

## SonarQube Integration

### Setup SonarQube:

1. Install and run SonarQube
2. Create a project in SonarQube
3. Generate an authentication token
4. Add the token as a Jenkins credential named `sonarqube-token`
5. Install the SonarQube Scanner for Jenkins plugin

### Configuration Files:

**sonar-project.properties:**
- Project identification
- Source and test paths
- JaCoCo coverage report paths
- Code coverage thresholds
- File exclusions

### Quality Gate:

The pipeline waits for SonarQube quality gate results and aborts if the gate fails.

## Code Coverage

- **Tool**: JaCoCo (Java Code Coverage)
- **Minimum Thresholds**: 50% (instruction, branch, line, complexity, method, class)
- **Report Location**: `target/site/jacoco/index.html`

## Artifacts

- **JAR File**: `target/java-jenkins-app-1.0.0.jar`
- **Test Reports**: `target/surefire-reports/`
- **Coverage Reports**: `target/site/jacoco/`

## Jenkins Configuration

### Required Plugins:
- Pipeline
- GitHub Integration
- SonarQube Scanner
- JUnit Plugin
- JaCoCo Plugin
- Workspace Cleanup Plugin

### Credentials:
- `sonarqube-token`: SonarQube authentication token

### Branch Configuration:
- Main branch: `main`
- Deploy stage runs only on main branch

## Logging

The project uses SLF4J with Logback for logging:
- **Logger**: SLF4J API
- **Implementation**: Logback
- **Config**: Default logback configuration

## Maven Dependencies

- **Testing**: JUnit 4.13.2, Mockito 4.8.1
- **Logging**: SLF4J 1.7.36, Logback 1.2.11
- **Code Coverage**: JaCoCo 0.8.8
- **Code Quality**: SonarQube Scanner 3.9.1.2184

## Troubleshooting

### SonarQube Connection Issues:
```bash
# Verify SonarQube is running
curl http://sonarqube:9000/api/system/ping
```

### Code Coverage Not Generated:
```bash
# Ensure JaCoCo plugin is configured in pom.xml
# Run tests before generating coverage
mvn clean test jacoco:report
```

### Jenkins Pipeline Failures:
- Check Jenkins logs: `Jenkins > Logs`
- Verify Java and Maven are installed in Jenkins agent
- Confirm SonarQube credentials are correct
- Review stage-specific errors in pipeline output

## Contributing

1. Create a feature branch
2. Make your changes
3. Write unit tests
4. Ensure all tests pass
5. Submit a pull request

## License

MIT License - See LICENSE file for details

## Support

For issues, questions, or suggestions, please create an issue in the repository.
