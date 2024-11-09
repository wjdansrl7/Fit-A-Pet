pipeline {
    agent any

    stages {
        stage('Build and Deploy backend-blue') {
            steps {
                script {
                    dir("${env.WORKSPACE}") {
                        // backend-blue 서비스 실행
                        sh 'docker-compose -f docker-compose.yml up -d --build backend-blue'
                    }
                }
            }
        }
        stage('Verify backend-blue') {
            steps {
                script {
                    retry(3) {
                        sleep 10
                        sh 'curl -f http://backend-blue:8082/health'
                    }
                }
            }
        }

        stage('Build and Deploy backend-green') {
            steps {
                script {
                    dir("${env.WORKSPACE}") {
                        // backend-green 서비스 실행
                        sh 'docker-compose -f docker-compose.yml up -d --build backend-green'
                    }
                }
            }
        }
        stage('Verify backend-green') {
            steps {
                script {
                    retry(3) {
                        sleep 10
                        sh 'curl -f http://backend-green:8083/health'
                    }
                }
            }
        }
    }
}
