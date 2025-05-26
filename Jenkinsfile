pipeline {
    agent any

    environment {
        // DockerHub info
        DOCKERHUB_IMAGE = 'mohamedjomaa1/investia'
        IMAGE_TAG = "latest"

        // DockerHub credentials stored in Jenkins (ID = dockerhub-credentials)
        DOCKER_CREDENTIALS_ID = 'f5a15a33-68b6-4c20-ad4b-5410c95ff103'

        // SonarQube (Jenkins global config ID)
     //   SONARQUBE_ENV = 'SonarQube' // change if needed
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/mohamedjomaa1/Devops-Investia.git'
            }
        }

        stage('Build Application') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

   /*   stage('SonarQube Analysis') {
            steps {
            withSonarQubeEnv('SonarQube') {
            bat '''
                mvn sonar:sonar ^
                    -Dsonar.projectKey=investia ^
                    -Dsonar.projectName=Investia ^
                    -Dsonar.host.url=%SONAR_HOST_URL% ^
                    -Dsonar.login=%SONAR_AUTH_TOKEN%
            '''
              }
            }
        } */

        /*stage('Unit Tests') {
    steps {
        bat 'mvn test'  // batch mode, error stacktrace, debug logs
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}*/



        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKERHUB_IMAGE%:%IMAGE_TAG% .'
            }
        }


        stage('Push to DockerHub') {
    steps {
        withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
            bat '''
                echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                docker push %DOCKERHUB_IMAGE%:%IMAGE_TAG%
            '''
        }
    }
}
        

        stage('Deploy with Docker Compose') {
            steps {
                bat '''
                    docker-compose down || true
                    docker-compose pull || true
                    docker-compose up -d --build
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build and deployment completed successfully!'
        }
        failure {
            echo '❌ Build or deployment failed. Check the logs.'
        }
    }
}
