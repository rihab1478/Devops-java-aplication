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

   stage ('Cleanup Artifacts') {
            steps {
                script {
                    sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker rmi ${IMAGE_NAME}:latest"
                }
            }
        }
   stage ('Updating kubernetes deployment file') {
            steps {
                script {
                    sh """
                    cat appdeploymentservice.yaml
                    sed -i 's/${APP_NAME}.*/${APP_NAME}:${IMAGE_TAG}/g' appdeploymentservice.yaml
                    cat appdeploymentservice.yaml
                    """
                }
            }
        }

           stage ('Push the changed deployment file to Git ') {
                    steps {
                        script {
                            sh """
                            git config --global user.name "rihab1478"
                            git config --global user.email "nabli.rihab@esprit.tn"
                            git add appdeploymentservice.yaml
                            git commit -m "updated the deployment file"
                            """
withCredentials([gitUsernamePassword(credentialsId: '61b89e9c-29f9-44f3-8552-6913b6b94407', gitToolName: 'Default')]) {                          sh """
                          git push https://github.com/rihab1478/Devsecops-java-aplication.git origin:main
                          """
                            }
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
