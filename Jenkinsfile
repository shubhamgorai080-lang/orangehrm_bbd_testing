pipeline {
 
    agent any
 
    tools {
        // Matches your Global Tool Configuration names
        jdk 'JDK25'
        maven 'Maven3'
        git 'Default' 
    }
 
    stages {
 
        stage('Git Checkout') {
            steps {
                // Pulls cleanly from your repository path
                git branch: 'main', url: 'https://github.com/shubhamgorai080-lang/orangehrm_bbd_testing.git'
            }
        }
 
        stage('Clean Project') {
            steps {
                bat 'mvn clean'
            }
        }
 
        stage('Run Tests') {
            steps {
                // Prevents test failures from abruptly crashing the pipeline build process
                bat 'mvn test -Dmaven.test.failure.ignore=true'
            }
        }
 
        stage('Publish Cucumber Report') {
            steps {
                // Publishes your test report right onto the Jenkins Job dashboard sidebar
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',
                    reportFiles: 'cucumber-reports.html',
                    reportName: 'OrangeHRM Report'
                ])
            }
        }
    }
 
    post {
 
        always {
            // Safely captures all generated HTML documents into your Jenkins build artifact section
            archiveArtifacts artifacts: 'target/*.html', allowEmptyArchive: true
        }
 
        success {
            echo 'Pipeline Executed Successfully'
        }
 
        failure {
            echo 'Pipeline Failed'
        }
    }
}

