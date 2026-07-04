# Setup Guide for Java Jenkins SonarQube Project

## Local Development Setup

### 1. Install Prerequisites

```bash
# Install Java 11
sudo apt-get install openjdk-11-jdk

# Install Maven
sudo apt-get install maven

# Verify installation
java -version
mvn -version
```

### 2. Clone and Build the Project

```bash
# Clone the repository
git clone https://github.com/Manjunath-Kapanaiah/JAVA-SONARQUBE_JENKINS.git
cd JAVA-SONARQUBE_JENKINS

# Build the project
mvn clean package
```

## Docker Setup (SonarQube + Jenkins)

### 1. Prerequisites

- Docker
- Docker Compose

### 2. Start Services

```bash
# Start all services
docker-compose up -d

# Verify services are running
docker-compose ps
```

### 3. Access Services

- **Jenkins**: http://localhost:8080 (admin/admin)
- **SonarQube**: http://localhost:9000 (admin/admin)
- **PostgreSQL**: localhost:5432

## Jenkins Configuration

### 1. Install Required Plugins

1. Go to **Manage Jenkins** > **Manage Plugins**
2. Search and install:
   - Pipeline
   - GitHub Integration
   - SonarQube Scanner
   - JUnit Plugin
   - JaCoCo Plugin
   - Workspace Cleanup
   - Email Extension

### 2. Configure System Settings

1. Go to **Manage Jenkins** > **Configure System**
2. Scroll to **SonarQube servers**
3. Add SonarQube server:
   - **Name**: SonarQube
   - **Server URL**: http://sonarqube:9000
   - **Server authentication token**: Create new token

### 3. Add Credentials

1. Go to **Manage Jenkins** > **Manage Credentials**
2. Click **Global credentials**
3. Add new credential:
   - **Kind**: Secret text
   - **Secret**: Your SonarQube authentication token
   - **ID**: sonarqube-token

### 4. Create New Pipeline Job

1. Click **New Item**
2. Enter job name: `java-jenkins-app`
3. Select **Pipeline**
4. Under **Pipeline**:
   - **Definition**: Pipeline script from SCM
   - **SCM**: Git
   - **Repository URL**: Your GitHub repository URL
   - **Script Path**: Jenkinsfile
5. Save and trigger build

## SonarQube Configuration

### 1. Generate Authentication Token

1. Login to SonarQube (http://localhost:9000)
2. Go to **My Account** > **Security**
3. Generate new token:
   - **Name**: jenkins-token
   - Copy the token and save it

### 2. Create Project

1. Go to **Projects** > **Create project**
2. **Project key**: java-jenkins-app
3. **Display name**: Java Jenkins Application
4. Click **Create**

### 3. Set Quality Gate

1. Go to **Quality Gates**
2. Create new or use existing quality gate
3. Configure rules and thresholds

## First Pipeline Run

### 1. Prepare Local Build

```bash
# Make sure Maven and Java are installed
mvn --version
java -version

# Build locally first to ensure it works
mvn clean package
```

### 2. Run Pipeline

1. Go to Jenkins dashboard
2. Select `java-jenkins-app` job
3. Click **Build Now**
4. Monitor the build progress

### 3. Review Results

- Check console output for build status
- View SonarQube analysis at: http://localhost:9000/dashboard
- Check code coverage reports in Jenkins artifacts

## Troubleshooting

### Docker Services Won't Start

```bash
# Check logs
docker-compose logs sonarqube
docker-compose logs jenkins

# Restart services
docker-compose restart
```

### SonarQube Connection Error in Jenkins

```bash
# Verify SonarQube is accessible
curl http://sonarqube:9000/api/system/health

# From Jenkins container
docker-compose exec jenkins curl http://sonarqube:9000/api/system/health
```

### Jenkins Can't Find Maven/Java

```bash
# Specify paths in Jenkins configuration
# In Jenkins job, set environment variables:
# export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
# export MAVEN_HOME=/usr/share/maven
# export PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH
```

### Tests Not Found

```bash
# Ensure tests are in src/test/java
# Run tests locally to verify
mvn test

# Check test naming convention (ends with Test.java)
```

## Cleanup

### Stop Docker Services

```bash
# Stop all services
docker-compose down

# Remove volumes (WARNING: deletes data)
docker-compose down -v
```

### Clean Local Build

```bash
# Remove build artifacts
mvn clean

# Or manually
rm -rf target/
```

## Next Steps

1. Configure webhook in GitHub for automatic triggers
2. Set up email notifications
3. Add deployment stage configuration
4. Customize quality gates and thresholds
5. Integrate with artifact repository (Nexus, Artifactory)

## References

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Docker Compose Reference](https://docs.docker.com/compose/)
