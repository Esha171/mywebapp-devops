pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose-ci.yml"
        // Get the email of the person who triggered the build (from GitHub push)
        GIT_COMMITTER_EMAIL = ""
    }

    stages {
        stage('Checkout') {
            steps {
                echo "🔄 Checking out code from GitHub"
                git branch: 'main', url: 'https://github.com/Esha171/mywebapp-devops.git', credentialsId: 'github-pat'
                script {
                    // Get the email of the last committer (person who pushed)
                    GIT_COMMITTER_EMAIL = sh(script: "git log -1 --pretty=format:'%ae'", returnStdout: true).trim()
                    echo "📧 Committer Email: ${GIT_COMMITTER_EMAIL}"
                }
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

        stage('Wait for Services') {
            steps {
                script {
                    echo "⏳ Waiting for services to be fully ready..."
                    sh "sleep 30"
                }
            }
        }

        stage('Run Selenium Tests') {
            steps {
                script {
                    echo "🧪 Running Selenium automated tests in Docker container..."
                    
                    // Get the host IP for the container to access the deployed app
                    def hostIP = sh(script: "hostname -I | awk '{print \$1}'", returnStdout: true).trim()
                    echo "🌐 Host IP: ${hostIP}"
                    
                    // Run Selenium tests using markhobson/maven-chrome Docker image
                    sh """
                        docker run --rm \
                            --network host \
                            -v \$(pwd)/selenium-tests:/app \
                            -w /app \
                            -e BASE_URL=http://${hostIP}:5173 \
                            markhobson/maven-chrome:3.9.6-jdk-11 \
                            mvn clean test -Dheadless=true
                    """
                }
            }
            post {
                always {
                    // Archive test results
                    script {
                        echo "📊 Archiving test results..."
                        sh "cp -r selenium-tests/target/surefire-reports . || true"
                    }
                    junit allowEmptyResults: true, testResults: 'surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            script {
                echo "📧 Sending email notification to: ${GIT_COMMITTER_EMAIL}"
            }
        }
        success {
            echo "🎉 Pipeline completed successfully!"
            emailext (
                subject: "✅ SUCCESS: Jenkins Pipeline - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <html>
                    <body>
                        <h2 style="color: green;">✅ Pipeline Succeeded!</h2>
                        <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                        <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                        <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                        <hr>
                        <h3>📊 Test Results Summary</h3>
                        <p>All Selenium automated tests passed successfully.</p>
                        <p>View detailed test reports: <a href="${env.BUILD_URL}testReport/">${env.BUILD_URL}testReport/</a></p>
                        <hr>
                        <p><em>This is an automated email from Jenkins CI/CD Pipeline.</em></p>
                        <p><strong>Paws & Claws Pet Adoption App - DevOps Assignment</strong></p>
                    </body>
                    </html>
                """,
                mimeType: 'text/html',
                to: "${GIT_COMMITTER_EMAIL}",
                attachLog: true
            )
        }
        failure {
            echo "❌ Pipeline failed. Check the logs."
            emailext (
                subject: "❌ FAILURE: Jenkins Pipeline - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <html>
                    <body>
                        <h2 style="color: red;">❌ Pipeline Failed!</h2>
                        <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                        <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                        <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                        <hr>
                        <h3>📊 Test Results</h3>
                        <p>Some tests may have failed. Please check the build logs and test reports.</p>
                        <p>View details: <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                        <hr>
                        <p><em>This is an automated email from Jenkins CI/CD Pipeline.</em></p>
                        <p><strong>Paws & Claws Pet Adoption App - DevOps Assignment</strong></p>
                    </body>
                    </html>
                """,
                mimeType: 'text/html',
                to: "${GIT_COMMITTER_EMAIL}",
                attachLog: true
            )
        }
    }
}
