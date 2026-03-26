pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/wonjoong2/HRMS.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew build -x test'
            }
        }

        stage('Run') {
            steps {
                sh '''
                pkill -f 'java -jar' || true
                nohup java -jar build/libs/*SNAPSHOT.jar > app.log 2>&1 &
                '''
            }
        }
    }
}