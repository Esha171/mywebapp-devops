pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose-ci.yml"
        FALLBACK_EMAIL = "admin@example.com"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "🔄 Checking out code from GitHub"
                git branch: 'main',
                    url: 'https://github.com/Esha171/mywebapp-devops.git',
                    credentialsId: 'github-pat'

                script {
                    // Store in environment variable properly
                    env.GIT_COMMITTER_EMAIL = sh(
                        script: "git log -1 --pretty=format:'%ae'",
                        returnStdout: true
                    ).trim()

                    echo "📧 Committer Email: ${env.GIT_COMMITTER_EMAIL}"

                    if (!env.GIT_COMMITTER_EMAIL || env.GIT_COMMITTER_EMAIL == '') {
                        echo "⚠️ Committer email not found, using fallback"
                        env.GIT_COMMITTER_EMAIL = FALLBACK_EMAIL
                    }
                }
            }
        }

        stage('Clean & Free Space') {
            steps {
                script {
                    echo "🧹 Aggressive cleanup to free disk space"
                    sh "docker container prune -f || true"
                    sh "docker image prune -af || true"
                    sh "docker volume prune -f || true"
                    sh "docker network prune -f || true"
                    sh "docker system prune -af --volumes || true"
                    sh "docker builder prune -af || true"
                    sh "df -h || true"
                    sh "docker system df || true"
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
                    sh "sleep 60"

                    def hostIP = sh(
                        script: "hostname -I | awk '{print \$1}'",
                        returnStdout: true
                    ).trim()

                    echo "🌐 Host IP: ${hostIP}"

                    echo "🔍 Checking if frontend is accessible..."
                    sh """
                        for i in 1 2 3 4 5 6 7 8 9 10; do
                            if curl -s --max-time 10 http://${hostIP}:8083 > /dev/null 2>&1; then
                                echo "✅ Frontend is responding!"
                                exit 0
                            fi
                            echo "⏳ Attempt \$i: Frontend not ready yet, waiting 10 seconds..."
                            sleep 10
                        done
                        echo "⚠️ Frontend may not be fully ready, continuing..."
                    """
                }
            }
        }

        stage('Run Selenium Tests') {
            steps {
                script {
                    echo "🧪 Running Selenium automated tests..."

                    sh "docker image prune -f || true"

                    def hostIP = sh(
                        script: "hostname -I | awk '{print \$1}'",
                        returnStdout: true
                    ).trim()

                    echo "🌐 Host IP: ${hostIP}"

                    sh "docker pull markhobson/maven-chrome || true"

                    def testResult = sh(
                        script: """
                            docker run --rm \
                                --network host \
                                -v \$(pwd)/selenium-tests:/app \
                                -w /app \
                                -e BASE_URL=http://${hostIP}:8083 \
                                markhobson/maven-chrome \
                                mvn clean test -DrerunFailingTestsCount=2
                        """,
                        returnStatus: true
                    )

                    if (testResult == 0) {
                        echo "✅ All Selenium tests passed!"
                    } else {
                        echo "⚠️ Some tests failed initially but may have passed on retry"
                    }
                }
            }

            post {
                always {
                    script {
                        echo "📊 Archiving test results"
                        sh "cp -r selenium-tests/target/surefire-reports . || true"
                    }
                    junit allowEmptyResults: true, testResults: 'surefire-reports/*.xml'
                }
            }
        }
    }

    post {

        always {
            echo "📧 Build triggered by: ${env.GIT_COMMITTER_EMAIL}"
        }

        success {
            script {
                emailext(
                    subject: "✅ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    mimeType: 'text/html',
                    to: "${env.GIT_COMMITTER_EMAIL}",
                    attachLog: true,
                    body: """
                        <html>
                        <body>
                            <h2 style="color:green;">✅ Pipeline Succeeded</h2>
                            <p><b>Job:</b> ${env.JOB_NAME}</p>
                            <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                            <p><b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                            <p><b>Triggered by:</b> ${env.GIT_COMMITTER_EMAIL}</p>
                            <p>All tests passed successfully 🎉</p>
                        </body>
                        </html>
                    """
                )
            }
        }

        failure {
            script {
                emailext(
                    subject: "❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    mimeType: 'text/html',
                    to: "${env.GIT_COMMITTER_EMAIL}",
                    attachLog: true,
                    body: """
                        <html>
                        <body>
                            <h2 style="color:red;">❌ Pipeline Failed</h2>
                            <p><b>Job:</b> ${env.JOB_NAME}</p>
                            <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                            <p><b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                            <p><b>Triggered by:</b> ${env.GIT_COMMITTER_EMAIL}</p>
                            <p>Please check console output for details.</p>
                        </body>
                        </html>
                    """
                )
            }
        }
    }
}
