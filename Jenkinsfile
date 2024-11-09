pipeline {
    agent any
    stages {
        stage('Build and Deploy backend-blue') {
            steps {
                script {
                    // Docker Compose 실행 디렉토리 이동
                    dir("${env.WORKSPACE}") {
                        // 기존 backend-blue 컨테이너 초기화
                        sh 'docker-compose -f docker-compose.yml down backend-blue'
                        // backend-blue 빌드 및 배포
                        sh 'docker-compose -f docker-compose.yml up -d --build backend-blue'
                    }
                }
            }
        }
        stage('Verify backend-blue') {
            steps {
                retry(3) {
                    sleep(time: 10, unit: 'SECONDS')
                    sh 'curl -f http://backend-blue:8082/health'
                }
            }
        }
        stage('Build and Deploy backend-green') {
            steps {
                script {
                    dir("${env.WORKSPACE}") {
                        sh 'docker-compose -f docker-compose.yml down backend-green'
                        sh 'docker-compose -f docker-compose.yml up -d --build backend-green'
                    }
                }
            }
        }
        stage('Verify backend-green') {
            steps {
                retry(3) {
                    sleep(time: 10, unit: 'SECONDS')
                    sh 'curl -f http://backend-green:8083/health'
                }
            }
        }
    }
}
