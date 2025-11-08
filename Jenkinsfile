pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose-ci.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull latest code from GitHub using your personal access token
                git branch: 'main', url: 'https://github.com/Esha171/mywebapp-devops.git', credentialsId: 'github-pat'
            }
        }

        stage('Build & Deploy') {
            steps {
                script {
                    // Stop and remove existing containers safely
                    sh "docker compose -f ${COMPOSE_FILE} down --remove-orphans"
                    
                    // Deploy containers in detached mode
                    sh "docker compose -f ${COMPOSE_FILE} up -d --build"
                }
            }
        }

        stage('Verify') {
            steps {
                script {
                    // List all running containers to verify deployment
                    sh "docker ps --format 'table {{.Names}}\t{{.Image}}\t{{.Ports}}\t{{.Status}}'"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment completed successfully!"
        }
        failure {
            echo "❌ Deployment failed. Check the logs."
        }
    }
}
