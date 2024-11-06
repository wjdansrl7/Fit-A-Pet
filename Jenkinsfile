pipeline {
    agent any


    
    stages {
        stage('Prepare') {
            steps {
                // 기존 컨테이너 종료
                script {
                    sh 'docker-compose -f docker-compose.yml down'
                }
            }
        }

        stage('Build and Deploy') {
            steps {
                script {
                    // Jenkins 작업 디렉토리에서 docker-compose 실행
                    dir("${env.WORKSPACE}") {
                        sh 'docker-compose -f docker-compose.yml down'
                        sh 'docker-compose -f docker-compose.yml up -d --build'
                    }
                }
            }
        }
    }
}

