pipeline {
    agent any
    environment {
        // Define DockerHub credentials (configured in Jenkins credentials)
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        // Define Docker image name and tag
        DOCKER_IMAGE = "mohamedjomaa1/investia"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        // SonarQube server configuration (configured in Jenkins global settings)
       // SONARQUBE_SERVER = 'sonarqube'
        // MySQL root password for Docker Compose
        MYSQL_ROOT_PASSWORD = credentials('mysql-root-password')
        // Stripe keys
        STRIPE_KEY_PUBLIC = credentials('stripe-key-public')
        STRIPE_KEY_SECRET = credentials('stripe-key-secret')
        // Mail credentials
        SPRING_MAIL_USERNAME = credentials('spring-mail-username')
        SPRING_MAIL_PASSWORD = credentials('spring-mail-password')
    }
    stages {
        stage('Checkout') {
            steps {
                // Checkout code from the updated Git repository
                git branch: 'master', url: 'https://github.com/mohamedjomaa1/Devops-Investia.git'
            }
        }
        stage('Build Application') {
            steps {
                // Run Maven build to compile and package the application
                sh 'mvn clean package -DskipTests'
            }
        }
        /*
        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis with Maven
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }*/
        stage('Unit Tests') {
            steps {
                // Run unit tests
                sh 'mvn test'
            }
            post {
                always {
                    // Publish JUnit test results
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                // Build Docker image using the Dockerfile
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
            }
        }
        stage('Push to DockerHub') {
            steps {
                // Log in to DockerHub and push images
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                sh "docker push ${DOCKER_IMAGE}:latest"
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                // Deploy using Docker Compose with environment variables
                sh '''
                    export MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD
                    export STRIPE_KEY_PUBLIC=$STRIPE_KEY_PUBLIC
                    export STRIPE_KEY_SECRET=$STRIPE_KEY_SECRET
                    export SPRING_MAIL_USERNAME=$SPRING_MAIL_USERNAME
                    export SPRING_MAIL_PASSWORD=$SPRING_MAIL_PASSWORD
                    docker-compose -f docker-compose.yaml up -d --build
                '''
            }
        }
    }
    post {
        always {
            // Clean up Docker images to save space
            sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || true"
            sh "docker rmi ${DOCKER_IMAGE}:latest || true"
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}