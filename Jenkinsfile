pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        DOCKER_IMAGE = "nileshbhujang/restapifwnb:${BUILD_NUMBER}"
        DOCKER_CREDENTIALS_ID = 'dockerhub_credentials'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/testnilesh7/RestAPIFramework.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE% ."
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat '''
                        echo Logging into Docker Hub...
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker push %DOCKER_IMAGE%
                    '''
                }
            }
        }

        stage('Run Sanity Tests on Dev') {
            steps {
                script {
                    def status = bat(
                        script: '''
                            echo Running Sanity Tests on Dev...
                            docker run --rm -v "%WORKSPACE%:/app" -w "/app" %DOCKER_IMAGE% ^
                            mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/GorestAPI.xml -Denv=prod
                        ''',
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Deploy to QA') {
            steps {
                echo 'Deploying to QA environment...'
            }
        }

        stage('Run Regression Tests on QA') {
            steps {
                script {
                    def status = bat(
                        script: '''
                            echo Running Regression Tests on QA...
                            docker run --rm -v "%WORKSPACE%:/app" -w "/app" %DOCKER_IMAGE% ^
                            mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/GorestAPI.xml -Denv=prod
                        ''',
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

        stage('Publish ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML API Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }

        stage('Deploy to Stage') {
            steps {
                echo 'Deploying to Stage environment...'
            }
        }

        stage('Run Sanity Tests on Stage') {
            steps {
                script {
                    def status = bat(
                        script: '''
                            echo Running Sanity Tests on Stage...
                            docker run --rm -v "%WORKSPACE%:/app" -w "/app" %DOCKER_IMAGE% ^
                            mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/GorestAPI.xml -Denv=prod
                        ''',
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish Sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML API Sanity ChainTest Report',
                    reportTitles: ''
                ])
            }
        }

        stage('Deploy to Prod') {
            steps {
                echo 'Deploying to Prod environment...'
            }
        }

        stage('Run Sanity Tests on Prod') {
            steps {
                script {
                    def status = bat(
                        script: '''
                            echo Running Sanity Tests on Prod...
                            docker run --rm -v "%WORKSPACE%:/app" -w "/app" %DOCKER_IMAGE% ^
                            mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/GorestAPI.xml -Denv=prod
                        ''',
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
    }
}
