#!/usr/bin/env groovy

/**
 * Jenkins Pipeline for Java project with SonarQube analysis
 * This pipeline builds, tests, and analyzes the Java project
 */

pipeline {
    agent any

    options {
        timeout(time: 1, unit: 'HOURS')
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10', daysToKeepStr: '30'))
    }

    parameters {
        string(name: 'SONAR_HOST_URL', defaultValue: 'http://sonarqube:9000', description: 'SonarQube server URL')
        string(name: 'SONAR_PROJECT_KEY', defaultValue: 'java-sonarqube-jenkins', description: 'SonarQube project key')
    }

    environment {
        MAVEN_HOME = '/usr/share/maven'
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk'
        PATH = "${env.MAVEN_HOME}/bin:${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '✓ Checking out code from repository...'
                checkout scm
                sh 'echo "Branch: ${GIT_BRANCH}" && echo "Commit: ${GIT_COMMIT}"'
            }
        }

        stage('Build') {
            steps {
                echo '✓ Building the project with Maven...'
                sh 'mvn clean compile -DskipTests -X'
            }
        }

        stage('Unit Tests') {
            steps {
                echo '✓ Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                    publishHTML([
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report'
                    ])
                }
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                echo '✓ Analyzing code with SonarQube...'
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.projectName="Java SonarQube Jenkins Project" \
                            -Dsonar.sources=src/main \
                            -Dsonar.tests=src/test \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                            -Dsonar.host.url=${SONAR_HOST_URL}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo '✓ Waiting for SonarQube Quality Gate...'
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package') {
            steps {
                echo '✓ Packaging the application...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '✓ Archiving build artifacts...'
                archiveArtifacts artifacts: 'target/java-sonarqube-jenkins.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo '✓ Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo '✅ Pipeline executed successfully!'
            // Add notification steps here (email, Slack, etc.)
        }
        failure {
            echo '❌ Pipeline failed!'
            // Add notification steps here (email, Slack, etc.)
        }
    }
}
