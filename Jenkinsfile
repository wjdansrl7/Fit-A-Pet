pipeline {
    agent any
    stages {
    	stage('Check File Access') {
            steps {
                script {
                    sh 'cat /home/ubuntu/docker-compose.yml'
                }
            }
        }
        stage('Build and Deploy') {
            steps {
                script {
                    // 정확한 경로로 docker-compose 명령 실행
                    sh 'docker-compose -f /home/ubuntu/docker-compose.yml down'
                    sh 'docker-compose -f /home/ubuntu/docker-compose.yml up -d'
                }
            }
        }
    }
}

