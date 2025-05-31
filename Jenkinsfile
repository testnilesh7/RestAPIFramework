pipeline 
{
    agent any
    
    tools{
        maven 'maven'
        }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
                
                
        stage('Regression API Automation Tests - QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/testnilesh7/RestAPIFramework.git'
                    bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/GorestAPI.xml -Denv=qa
                    "
                    
                }
            }
        }
                
     
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
        stage('Publish ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML API Regression ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        stage('Sanity API Automation Test - Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/testnilesh7/RestAPIFramework.git'
                    bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/GorestAPI.xml -Denv=stage"
                    
                }
            }
        }
        
        
        stage('Publish sanity ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML API Sanity ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }
        
        stage('Sanity API Automation Test - Prod') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/testnilesh7/RestAPIFramework.git'
                    bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/GorestAPI.xml -Denv=prod"
                    
                }
            }
        }
        
        
    }
}