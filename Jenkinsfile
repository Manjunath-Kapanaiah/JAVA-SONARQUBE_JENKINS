pipeline {
    agent any

    parameters {
        string(name: 'SONAR_HOST_URL', defaultValue: 'http://sonarqube:9000', description: 'SonarQube Server URL')
        string(name: 'SONAR_LOGIN', defaultValue: 'admin', description: 'SonarQube Login Token')
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}"
        SONAR_HOST_URL = "${params.SONAR_HOST_URL}"
        SONAR_LOGIN = credentials('sonarqube-token')
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
        timeout(time: 1, unit: 'HOURS')
        timestamps()
    }

    triggers {
        // Trigger on push to main branch
        githubPush()
        // Poll SCM every 15 minutes
        pollSCM('H/15 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "========== Checking out code =========="
                }
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "========== Building Java Project =========="
                }
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                script {
                    echo "========== Running Unit Tests =========="
                }
                sh 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                script {
                    echo "========== Generating Code Coverage Report =========="
                }
                sh 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "========== Running SonarQube Analysis =========="
                }
                withSonarQubeEnv('SonarQube') {
                    sh '''mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=java-jenkins-app \
                        -Dsonar.projectName="Java Jenkins Application" \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.java.coveragePlugin=jacoco \
                        -Dsonar.jacoco.reportPath=target/jacoco.exec \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java'''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    echo "========== Waiting for Quality Gate =========="
                }
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    echo "========== Packaging Application =========="
                }
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifacts') {
            steps {
                script {
                    echo "========== Archiving Build Artifacts =========="
                }
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                archiveArtifacts artifacts: 'target/site/**', allowEmptyArchive: true
            }
        }

        stage('Publish Reports') {
            steps {
                script {
                    echo "========== Publishing Test Reports =========="
                }
                junit 'target/surefire-reports/*.xml'
                jacoco(
                    execPattern: 'target/jacoco.exec',
                    classPattern: 'target/classes',
                    sourcePattern: 'src/main/java',
                    exclusionPattern: 'src/test*',
                    changeBuildStatus: true,
                    minimumInstructionCoverage: '50',
                    minimumBranchCoverage: '50',
                    minimumLineCoverage: '50',
                    minimumComplexityCoverage: '50',
                    minimumMethodCoverage: '50',
                    minimumClassCoverage: '50'
                )
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                script {
                    echo "========== Deploying Application =========="
                }
                // Add your deployment steps here
                sh 'echo "Deployment step - Customize as per your environment"'
            }
        }
    }

    post {
        always {
            script {
                echo "========== Pipeline Completed =========="
            }
            cleanWs()
        }

        success {
            script {
                echo "========== Build Successful =========="
            }
            // Add notification steps here
        }

        failure {
            script {
                echo "========== Build Failed =========="
            }
            // Add notification steps here
        }

        unstable {
            script {
                echo "========== Build Unstable =========="
            }
            // Add notification steps here
        }
    }
}
