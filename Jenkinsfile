pipeline {
    agent any

    environment {
        DB_PASSWORD = credentials('DB_PASSWORD')
        OPENAI_API_KEY = credentials('OPENAI_API_KEY')
        MAIL_PASSWORD = credentials('MAIL_PASSWORD')
    }

    stages {

        stage('Git Clone') {
            steps {
                git
                    url: 'https://github.com/wonjoong2/HRMS.git',
                    branch: 'master'
            }
        }

        stage('Build') {
            steps {
                sh '''
                chmod +x gradlew
                ./gradlew clean build -x test
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                pkill -f 'java -jar' || true

                BUILD_ID=dontKillMe nohup java -jar build/libs/*.jar \
                > app.log 2>&1 &
                '''
            }
        }
    }
}