pipeline {
    agent any

      environment {
          DOCKER_HUB_USERNAME = 'rihab23'
          SCANNER_HOME = tool 'SonarScanner'
           APP_NAME = "devsecopsapplication"
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
                sh "mvn clean package -Plinux"
            }

        }

        stage("Test Application"){
            steps {
                sh "mvn test"
                sh "ls -R target/surefire-reports"
            }
            post{
                always{
                   junit '**/target/surefire-reports/TEST-*.xml'
                    //junit testResults: 'results.xml', skipPublishingChecks: true

                }
            }

        }


    stage("Sonarqube Analysis "){
                steps{
                    withSonarQubeEnv('SonarServer') {
                        sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=java-devsecops-test\
                        -Dsonar.projectKey=java-devsecops-test\
                        -Dsonar.java.binaries=target/classes '''


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
                             stage("Run Dockerized Application") {
                                         steps {
                                             script {
                                                 docker.image("${IMAGE_NAME}:${IMAGE_TAG}").pull()

                                                 def appContainer = docker.container("${IMAGE_NAME}:${IMAGE_TAG}").run("-p 8072:8072")

                                                 sleep(time: 60, unit: 'SECONDS')

                                                 sh("xdg-open http://localhost:8072/api/users/index")

                                                 appContainer.stop()
                                                 appContainer.remove()
                                             }
                                         }
                                     }
                                     stage("trivy scanne ") {
                                         steps {
                                             script {
                                                     sh 'trivy image ${DOCKER_HUB_USERNAME}/devsecops-java-project:latest'
                                             }
                                         }

                                     }
                                     }
       post {
            failure {
                emailext body: '''${SCRIPT, template="groovy-html.template"}''',
                        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Failed",
                        mimeType: 'text/html',to: "applicationtest40@gmail.com"
                }
             success {
                   emailext body: '''${SCRIPT, template="groovy-html.template"}''',
                        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Successful",
                        mimeType: 'text/html',to: "applicationtest40@gmail.com"
              }
        }
}
