pipeline {
    agent any

      environment {
          DOCKER_HUB_USERNAME = 'rihab23'
          SCANNER_HOME = tool 'SonarScanner'
           APP_NAME = "devsecops-pipeline"
          RELEASE = "1.0.0"
          DOCKER_USER = "rihab23"
          DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"

         }
    tools{
        maven 'maven'
    }
    stages{
        stage("Cleanup Workspace"){
            steps {
                cleanWs()
            }

        }

        stage("Checkout from SCM"){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/rihab1478/Devsecops-java-aplication']]])


            }
        }
                stage("Build Application"){
            steps {
                sh "mvn clean package"
            }

        }

        stage("Test Application"){
            steps {
                sh "mvn test"
            }

        }

         stage("Sonarqube Analysis "){
            steps{
                withSonarQubeEnv('SonarServer') {
                    sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=java-devsecops-test\
                    -Dsonar.projectKey=java-devsecops-test '''

                }
            }
        }
        stage("Quality Gate "){
            steps{

                   waitForQualityGate abortPipeline: false, credentialsId: 'javaid'
            }
        }
        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }

                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }
         }
        stage("trivy scanne ") {
            steps {
                script {

                        sh ('trivy image ${DOCKER_HUB_USERNAME}/devsecops-java-project:latest')

                }
            }

        }
    }
}