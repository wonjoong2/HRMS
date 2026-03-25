pipeline {
    agent any

    environment {
        DB_PASSWORD = credentials('DB_PASSWORD')
        OPENAI_API_KEY = credentials('OPENAI_API_KEY')
        MAIL_PASSWORD = credentials('MAIL_PASSWORD')
    }

    stages {

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

                BUILD_ID=dontKillMe nohup java -jar build/libs/hrms-0.0.1-SNAPSHOT.jar \
                --spring.datasource.password=$DB_PASSWORD \
                --spring.mail.password=$MAIL_PASSWORD \
                --openai.api-key=$OPENAI_API_KEY \
                > app.log 2>&1 &
                '''
            }
        }
    }
}