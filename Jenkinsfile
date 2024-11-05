pipeline {
    agent any
    stages {
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

