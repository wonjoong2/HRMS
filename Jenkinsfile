pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew build -x test'
            }
        }

        stage('Run') {
            steps {
                withCredentials([
                    string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD'),
                    string(credentialsId: 'OPENAI_API_KEY', variable: 'OPENAI_API_KEY'),
                    string(credentialsId: 'MAIL_PASSWORD', variable: 'MAIL_PASSWORD')
                ]) {
                    sh '''
                    pkill -f 'java -jar' || true

                    JAR_FILE=$(ls build/libs/*.jar | grep -v plain | head -n 1)

                    java -jar $JAR_FILE --server.port=8082
                    '''
                }
            }
        }
    }
}