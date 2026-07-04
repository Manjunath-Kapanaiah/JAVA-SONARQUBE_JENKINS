pipeline {
    agent any

    parameters {
        string(name: 'SONAR_HOST_URL', defaultValue: 'http://sonarqube:9000', description: 'SonarQube Server URL')
        choice(name: 'BUILD_TYPE', choices: ['dev', 'staging', 'production'], description: 'Build environment')
    }

    environment {
        // Java and Maven Configuration
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}"
        
        // SonarQube Configuration
        SONAR_HOST_URL = "${params.SONAR_HOST_URL}"
        SONAR_LOGIN = credentials('sonarqube-token')
        
        // Project Configuration
        PROJECT_NAME = 'java-jenkins-app'
        PROJECT_VERSION = '1.0.0'
        BUILD_TIMESTAMP = sh(script: 'date +%Y%m%d_%H%M%S', returnStdout: true).trim()
        
        // Artifact Configuration
        ARTIFACT_NAME = "${PROJECT_NAME}-${PROJECT_VERSION}-${BUILD_TIMESTAMP}.jar"
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '10'))
        timeout(time: 2, unit: 'HOURS')
        timestamps()
        ansiColor('xterm')
    }

    triggers {
        // Trigger on GitHub push
        githubPush()
        
        // Poll SCM every 15 minutes
        pollSCM('H/15 * * * *')
    }

    stages {
        stage('Pre-Build Validation') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Pre-Build Validation Starting            ║"
                    echo "╚═════════════════════════════════════════════╝"
                    
                    // Check Java version
                    sh '''
                        echo "Java Version:"
                        java -version
                        echo ""
                        echo "Maven Version:"
                        mvn -version
                    '''
                }
            }
        }

        stage('Checkout') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Checking Out Code from Repository        ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                checkout scm
                
                script {
                    // Display commit information
                    sh '''
                        echo "Commit Information:"
                        git log -1 --oneline
                        echo ""
                        echo "Branch: $(git rev-parse --abbrev-ref HEAD)"
                    '''
                }
            }
        }

        stage('Dependency Check') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Checking Dependencies                    ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                sh 'mvn dependency:resolve -q'
                sh 'mvn dependency:tree -q'
            }
        }

        stage('Clean & Compile') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Cleaning and Compiling Java Project      ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                sh 'mvn clean compile -DskipTests -q'
                
                script {
                    echo "✓ Compilation successful"
                }
            }
        }

        stage('Unit Tests') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Running Unit Tests                       ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                sh '''
                    mvn test \
                        -Dorg.slf4j.simpleLogger.defaultLogLevel=info \
                        -DtrimStackTrace=false
                '''
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Generating Code Coverage Report          ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                sh '''
                    mvn jacoco:report \
                        -DskipTests=true \
                        -q
                '''
                
                script {
                    // Display coverage summary
                    sh '''
                        echo ""
                        echo "Code Coverage Report Generated:"
                        if [ -f target/site/jacoco/index.html ]; then
                            echo "✓ Report available at: target/site/jacoco/index.html"
                        fi
                    '''
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Running SonarQube Code Analysis          ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=${PROJECT_NAME} \
                            -Dsonar.projectName="Java Jenkins Application" \
                            -Dsonar.projectVersion=${PROJECT_VERSION} \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.java.libraries=target/dependency \
                            -Dsonar.java.coveragePlugin=jacoco \
                            -Dsonar.jacoco.reportPath=target/jacoco.exec \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                            -Dsonar.sources=src/main/java \
                            -Dsonar.tests=src/test/java \
                            -Dsonar.test.inclusions=**/*Test.java \
                            -Dsonar.coverage.inclusions=src/main/java/**/*.java \
                            -Dsonar.exclusions=**/test/**,**/target/** \
                            -DskipTests=true
                    '''
                }
                
                script {
                    echo "✓ SonarQube analysis completed"
                    echo "  Project Key: ${PROJECT_NAME}"
                    echo "  SonarQube URL: ${SONAR_HOST_URL}"
                }
            }
        }

        stage('Quality Gate Check') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Waiting for SonarQube Quality Gate       ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
                
                script {
                    echo "✓ Quality Gate PASSED"
                }
            }
        }

        stage('Build & Package') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Building and Packaging Application       ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                sh '''
                    mvn package -DskipTests -q
                '''
                
                script {
                    sh '''
                        echo ""
                        echo "Build Artifacts:"
                        ls -lh target/*.jar 2>/dev/null || echo "No JAR files found"
                    '''
                }
            }
        }

        stage('Archive & Publish Reports') {
            steps {
                script {
                    echo "╔═════════════════════════════════════════════╗"
                    echo "║    Archiving Artifacts & Publishing Reports ║"
                    echo "╚═════════════════════════════════════════════╝"
                }
                
                // Archive JAR artifacts
                archiveArtifacts artifacts: 'target/*.jar', 
                                 allowEmptyArchive: true,
                                 fingerprint: true
                
                // Archive coverage reports
                archiveArtifacts artifacts: 'target/site/jacoco/**', 
                                 allowEmptyArchive: true
                
                // Publish JUnit Test Results
                junit testResults: 'target/surefire-reports/*.xml', 
                      allowEmptyResults: true
                
                // Publish JaCoCo Code Coverage
                jacoco(
                    execPattern: 'target/jacoco.exec',
                    classPattern: 'target/classes',
                    sourcePattern: 'src/main/java',
                    exclusionPattern: 'src/test*',
                    changeBuildStatus: true,
                    minimumInstructionCoverage: '40',
                    minimumBranchCoverage: '40',
                    minimumLineCoverage: '40',
                    minimumComplexityCoverage: '40',
                    minimumMethodCoverage: '40',
                    minimumClassCoverage: '40'\n                )\n                \n                script {\n                    echo \"✓ Test reports published\"\n                    echo \"✓ Coverage reports published\"\n                }\n            }\n        }\n\n        stage('Quality Report Generation') {\n            steps {\n                script {\n                    echo \"╔═════════════════════════════════════════════╗\"\n                    echo \"║    Generating Quality Report                ║\"\n                    echo \"╚═════════════════════════════════════════════╝\"\n                }\n                \n                sh '''\n                    echo \"\"\n                    echo \"=== BUILD SUMMARY ===\"\n                    echo \"Project: ${PROJECT_NAME}\"\n                    echo \"Version: ${PROJECT_VERSION}\"\n                    echo \"Build Timestamp: ${BUILD_TIMESTAMP}\"\n                    echo \"Build Number: #${BUILD_NUMBER}\"\n                    echo \"Build Status: SUCCESS\"\n                    echo \"\"\n                    echo \"=== CODE QUALITY REPORTS ===\"\n                    echo \"1. JaCoCo Code Coverage Report:\"\n                    echo \"   Location: target/site/jacoco/index.html\"\n                    echo \"\"\n                    echo \"2. SonarQube Analysis Report:\"\n                    echo \"   URL: ${SONAR_HOST_URL}/dashboard?id=${PROJECT_NAME}\"\n                    echo \"\"\n                    echo \"3. JUnit Test Reports:\"\n                    echo \"   Location: target/surefire-reports/\"\n                    echo \"\"\n                    echo \"4. Build Artifacts:\"\n                    ls -lh target/*.jar 2>/dev/null || echo \"No artifacts generated\"\n                    echo \"\"\n                    echo \"=== NEXT STEPS ===\"\n                    echo \"- Review SonarQube dashboard: ${SONAR_HOST_URL}\"\n                    echo \"- Check coverage reports in Jenkins: Build > Artifacts\"\n                    echo \"- Deploy application: manual or automated deployment\"\n                '''\n            }\n        }\n\n        stage('Deploy') {\n            when {\n                branch 'main'\n            }\n            steps {\n                script {\n                    echo \"╔═════════════════════════════════════════════╗\"\n                    echo \"║    Deploying Application                    ║\"\n                    echo \"╚═════════════════════════════════════════════╝\"\n                    \n                    echo \"Build Type: ${params.BUILD_TYPE}\"\n                }\n                \n                sh '''\n                    echo \"\"\n                    echo \"Deployment Configuration:\"\n                    echo \"- Build Type: ${BUILD_TYPE}\"\n                    echo \"- Artifact: ${ARTIFACT_NAME}\"\n                    echo \"\"\n                    echo \"Custom deployment logic goes here\"\n                    echo \"- Copy artifacts to deployment server\"\n                    echo \"- Run database migrations if needed\"\n                    echo \"- Restart application servers\"\n                    echo \"- Run smoke tests\"\n                '''\n            }\n        }\n    }\n\n    post {\n        always {\n            script {\n                echo \"╔═════════════════════════════════════════════╗\"\n                echo \"║    Pipeline Execution Complete              ║\"\n                echo \"╚═════════════════════════════════════════════╝\"\n            }\n            \n            // Clean workspace\n            cleanWs(\n                deleteDirs: true,\n                patterns: [\n                    [pattern: 'target/', type: 'INCLUDE']\n                ]\n            )\n        }\n\n        success {\n            script {\n                echo \"╔═════════════════════════════════════════════╗\"\n                echo \"║    ✓ BUILD SUCCESSFUL                       ║\"\n                echo \"╚═════════════════════════════════════════════╝\"\n                \n                sh '''\n                    echo \"\"\n                    echo \"Build Details:\"\n                    echo \"  - Build Number: #${BUILD_NUMBER}\"\n                    echo \"  - Build URL: ${BUILD_URL}\"\n                    echo \"  - Status: SUCCESS\"\n                    echo \"\"\n                    echo \"Reports Available:\"\n                    echo \"  - JaCoCo: ${BUILD_URL}Code-Coverage/\"\n                    echo \"  - JUnit: ${BUILD_URL}testReport/\"\n                '''\n            }\n            \n            // Add notification here (email, Slack, etc.)\n        }\n\n        failure {\n            script {\n                echo \"╔═════════════════════════════════════════════╗\"\n                echo \"║    ✗ BUILD FAILED                           ║\"\n                echo \"╚═════════════════════════════════════════════╝\"\n                \n                sh '''\n                    echo \"\"\n                    echo \"Build Details:\"\n                    echo \"  - Build Number: #${BUILD_NUMBER}\"\n                    echo \"  - Build URL: ${BUILD_URL}\"\n                    echo \"  - Status: FAILED\"\n                    echo \"\"\n                    echo \"Troubleshooting:\"\n                    echo \"  1. Check build logs: ${BUILD_URL}console\"\n                    echo \"  2. Verify Java/Maven installation\"\n                    echo \"  3. Check SonarQube connectivity\"\n                    echo \"  4. Review quality gate thresholds\"\n                '''\n            }\n            \n            // Add notification here (email, Slack, etc.)\n        }\n\n        unstable {\n            script {\n                echo \"╔═════════════════════════════════════════════╗\"\n                echo \"║    ⚠ BUILD UNSTABLE                         ║\"\n                echo \"╚═════════════════════════════════════════════╝\"\n                \n                echo \"Some tests failed or quality gates are below threshold\"\n            }\n        }\n\n        aborted {\n            script {\n                echo \"╔═════════════════════════════════════════════╗\"\n                echo \"║    ⊘ BUILD ABORTED                          ║\"\n                echo \"╚═════════════════════════════════════════════╝\"\n            }\n        }\n    }\n}\n