pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose-ci.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Esha171/mywebapp-devops.git', credentialsId: 'github-pat'
            }
        }

        stage('Build & Deploy Frontend') {
            steps {
                script {
                    echo "🛑 Removing old frontend container if it exists"
                    sh "docker rm -f ci_frontend || true"  // ignore error if container doesn't exist

                    echo "📦 Building new frontend image"
                    sh "docker build -t my-frontend-fixed ./frontend"

                    echo "🚀 Running new frontend container"
                    sh "docker run -d -p 8083:80 --name ci_frontend my-frontend-fixed"

                    echo "🔍 Verifying frontend files inside container"
                    sh "docker exec ci_frontend ls /usr/share/nginx/html"
                }
            }
        }

        stage('Deploy Backend & Other Services') {
            steps {
                script {
                    echo "🛠 Bringing up backend/services via docker-compose"
                    sh "docker compose -f ${COMPOSE_FILE} up -d --build"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
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
