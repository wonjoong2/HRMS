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

                JAR_FILE=$(ls build/libs/*.jar | grep -v plain | head -n 1)

                echo "실행 시작"

                java -jar $JAR_FILE --server.port=8082
                '''
            }
        }
    }
}