pipeline {
    agent any

    stages {
        stage('Build and Deploy backend-blue') {
            steps {
                script {
                    dir("${env.WORKSPACE}") {
                        // backend-blue 빌드 및 배포
                        sh 'docker-compose -f docker-compose.yml up -d --build backend-blue'
                    }
                }
            }
        }

        stage('Verify backend-blue') {
            steps {
                script {
                    // backend-blue health check
                    sh 'curl -f http://backend-blue:8082/health || exit 1'
                }
            }
        }

        stage('Build and Deploy backend-green') {
            steps {
                script {
                    dir("${env.WORKSPACE}") {
                        // backend-green 빌드 및 배포
                        sh 'docker-compose -f docker-compose.yml up -d --build backend-green'
                    }
                }
            }
        }

        stage('Verify backend-green') {
            steps {
                script {
                    // backend-green health check
                    sh 'curl -f http://backend-green:8083/health || exit 1'
                }
            }
        }
    }
}
