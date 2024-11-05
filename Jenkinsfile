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
                    // Jenkins 작업 디렉토리에서 docker-compose 실행
                    dir("${env.WORKSPACE}") {
                        sh 'docker-compose -f docker-compose.yml down'
                        sh 'docker-compose -f docker-compose.yml up -d'
                    }
                }
            }
        }
    }
}

