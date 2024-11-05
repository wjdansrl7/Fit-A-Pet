pipeline {
    agent any
    stages {
        stage('Build and Deploy') {
            steps {
                script {
                    // `docker-compose.yml` 파일이 있는 디렉토리에서 명령을 실행
                    dir("${env.WORKSPACE}") {
                        sh 'docker-compose down'
                        sh 'docker-compose up -d'
                    }
                }
            }
        }
    }
}

