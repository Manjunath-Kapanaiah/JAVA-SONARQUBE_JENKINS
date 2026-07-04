# Java SonarQube Jenkins Project

A comprehensive Java project demonstrating integration with SonarQube for code quality analysis and Jenkins for CI/CD pipeline execution.

## Project Overview

This project includes:
- ✅ **Calculator Service**: Basic arithmetic operations with comprehensive unit tests
- ✅ **User Service**: User management utilities with validation
- ✅ **Logging**: SLF4J with Logback for structured logging
- ✅ **Testing**: JUnit 4 with comprehensive test coverage
- ✅ **Code Coverage**: JaCoCo for code coverage analysis
- ✅ **SonarQube Integration**: Code quality and security analysis
- ✅ **Jenkins Pipeline**: Automated CI/CD with Jenkinsfile

## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Application.java
│   │   │   ├── Calculator.java
│   │   │   └── UserService.java
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
│       └── java/com/example/
│           ├── CalculatorTest.java
│           └── UserServiceTest.java
├── pom.xml
├── Jenkinsfile
└── README.md
```

## Prerequisites

- **Java 11+**: Required for compilation and execution
- **Maven 3.6+**: For building and dependency management
- **Jenkins**: For CI/CD pipeline execution
- **SonarQube**: For code quality analysis

## Building the Project

### Clean Build
```bash
mvn clean install
```

### Build without Tests
```bash
mvn clean compile -DskipTests
```

### Run Tests
```bash
mvn test
```

### Generate JaCoCo Report
```bash
mvn clean test jacoco:report
```

## SonarQube Analysis

### Local SonarQube Analysis
```bash
mvn clean test sonar:sonar \
  -Dsonar.projectKey=java-sonarqube-jenkins \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<your-sonarqube-token>
```

### In Docker
```bash
docker run -d --name sonarqube -e SONARQUBE_JDBC_URL=jdbc:postgresql://db:5432/sonarqube -p 9000:9000 sonarqube:latest
```

## Jenkins Pipeline

The `Jenkinsfile` defines a complete CI/CD pipeline:

### Pipeline Stages
1. **Checkout**: Clone the repository
2. **Build**: Compile the project
3. **Unit Tests**: Execute test suite and generate reports
4. **Code Analysis**: Run SonarQube analysis
5. **Quality Gate**: Verify SonarQube quality gates
6. **Package**: Create executable JAR
7. **Archive Artifacts**: Store build artifacts

### Running the Pipeline

1. Configure Jenkins credentials for SonarQube
2. Add the repository to Jenkins
3. Create a new Pipeline job using the `Jenkinsfile`
4. Run the job

## Running the Application

### From Command Line
```bash
mvn clean compile exec:java -Dexec.mainClass="com.example.Application"
```

### From JAR
```bash
mvn clean package
java -jar target/java-sonarqube-jenkins.jar
```

## Test Coverage

The project includes comprehensive unit tests for:
- Addition, subtraction, multiplication, and division
- Edge cases (negative numbers, zero, division by zero)
- User validation and age verification

### Expected Test Results
- ✅ **CalculatorTest**: 9 test cases
- ✅ **UserServiceTest**: 6 test cases
- Total: 15 test cases

## Code Quality Metrics

The project is configured to track:
- **Code Coverage**: Target > 80%
- **Code Smells**: Issues affecting maintainability
- **Bugs**: Potential defects
- **Vulnerabilities**: Security issues
- **Duplications**: Code duplication detection

## Dependencies

### Test Dependencies
- **JUnit 4.13.2**: Unit testing framework
- **Mockito 4.8.0**: Mocking library

### Logging
- **SLF4J 1.7.36**: Logging facade
- **Logback 1.2.11**: Logging implementation

### Build Plugins
- **Maven Compiler**: Java 11 compilation
- **Maven Surefire**: Test execution
- **JaCoCo**: Code coverage
- **SonarQube Scanner**: Code analysis
- **Maven Assembly**: JAR packaging

## Logging Configuration

Logs are configured in `src/main/resources/logback.xml`:
- **Console**: Real-time log output
- **File**: Logs written to `logs/application.log`
- **Rolling**: Automatic log rotation (10MB per file)
- **Retention**: 30 days of log history

## Contributing

1. Create a new branch for your feature
2. Commit your changes
3. Push to the repository
4. Create a pull request

## License

MIT License

## Support

For issues or questions, please create an issue in the GitHub repository.
