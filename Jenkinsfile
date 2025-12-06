pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose-ci.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "🔄 Checking out code from GitHub"
                git branch: 'main', url: 'https://github.com/Esha171/mywebapp-devops.git', credentialsId: 'github-pat'
            }
        }

        stage('Clean & Free Space') {
            steps {
                script {
                    echo "🧹 Cleaning Docker system to free space"
                    sh "docker system prune -af || true"
                    sh "docker volume prune -f || true"
                }
            }
        }

        stage('Deploy Backend & All Services') {
            steps {
                script {
                    echo "🛠 Bringing down old containers (if any)"
                    sh "docker compose -f ${COMPOSE_FILE} down || true"
                    echo "🛠 Building and deploying everything with docker-compose"
                    sh "docker compose -f ${COMPOSE_FILE} up -d --build"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo "✅ Listing all running containers"
                    sh "docker ps --format 'table {{.Names}}\t{{.Image}}\t{{.Ports}}\t{{.Status}}'"
                }
            }
        }
    }

    post {
        success {
            echo "🎉 Deployment completed successfully!"
        }
        failure {
            echo "❌ Deployment failed. Check the logs."
        }
    }
}
