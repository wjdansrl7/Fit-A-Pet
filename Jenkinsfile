pipeline {
    agent any
    stages {
        stage('Build and Deploy') {
            steps {
                script {
                    sh 'docker-compose down'  // 기존 실행 중인 컨테이너 종료
                    sh 'docker-compose up -d --build'  // 도커 컴포즈로 빌드 및 실행
                }
            }
        }
    }
}

