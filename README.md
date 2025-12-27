# 🐾 Paws & Claws - Pet Adoption Web Application

A full-stack pet adoption web application with DevOps CI/CD pipeline, Docker containerization, and automated Selenium testing. This project demonstrates modern web development practices with comprehensive DevOps implementation.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [CI/CD Pipeline](#cicd-pipeline)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)

## 🎯 Overview

Paws & Claws is a comprehensive pet adoption platform that enables users to:
- Browse and adopt pets
- Shop for pet supplies (food and accessories)
- Manage pet listings through an admin panel
- Complete adoption forms and process applications

The application features a complete DevOps pipeline with Jenkins CI/CD, Docker containerization, and automated Selenium tests for quality assurance.

## 🏗️ Architecture

The application follows a **microservices architecture** with three main components:

```
┌─────────────────────────────────────────────────────────────────┐
│                         Load Balancer / Nginx                    │
└─────────────────────────────────────────────────────────────────┘
           │                    │                    │
           ▼                    ▼                    ▼
    ┌──────────┐         ┌──────────┐        ┌──────────┐
    │ Frontend │         │  Admin   │        │ Backend  │
    │  (React) │         │ (React)  │        │(Node.js) │
    │ Port: 80 │         │Port: 8080│        │Port: 4000│
    └──────────┘         └──────────┘        └──────────┘
                                                    │
                                                    ▼
                                            ┌──────────────┐
                                            │ MongoDB Atlas│
                                            │   (Cloud)    │
                                            └──────────────┘
```

### Components:

1. **Frontend** - User-facing React application with Vite, TailwindCSS, and DaisyUI
2. **Admin Panel** - React-based admin interface for managing pets and orders
3. **Backend** - Node.js/Express REST API with MongoDB integration
4. **Database** - MongoDB Atlas (cloud-hosted)

## 🛠️ Technology Stack

### Frontend & Admin
- **Framework:** React 18
- **Build Tool:** Vite
- **Styling:** TailwindCSS, DaisyUI
- **Routing:** React Router v7
- **HTTP Client:** Axios
- **Carousel:** React Slick

### Backend
- **Runtime:** Node.js
- **Framework:** Express.js
- **Database:** MongoDB with Mongoose ODM
- **Authentication:** JWT (JSON Web Tokens)
- **Password Hashing:** bcrypt
- **File Upload:** Multer
- **Validation:** Validator.js
- **Payment:** Stripe Integration

### DevOps & Infrastructure
- **Containerization:** Docker, Docker Compose
- **CI/CD:** Jenkins Pipeline
- **Testing:** Selenium WebDriver with TestNG
- **Version Control:** Git & GitHub
- **Deployment:** AWS EC2 (via Jenkins)

## 📁 Project Structure

```
mywebapp-devops/
├── frontend/               # User-facing React application
│   ├── src/
│   │   ├── components/     # Reusable React components
│   │   ├── pages/          # Page components
│   │   ├── context/        # React Context for state management
│   │   └── assets/         # Images and static files
│   ├── Dockerfile          # Frontend container configuration
│   └── package.json        # Frontend dependencies
│
├── admin/                  # Admin panel React application
│   ├── src/
│   │   ├── components/     # Admin components
│   │   └── pages/          # Admin pages
│   ├── Dockerfile          # Admin container configuration
│   └── package.json        # Admin dependencies
│
├── backend/                # Node.js/Express API server
│   ├── controllers/        # Request handlers
│   ├── models/            # MongoDB models
│   ├── routes/            # API routes
│   ├── config/            # Database configuration
│   ├── uploads/           # File upload directory
│   ├── server.js          # Entry point
│   ├── Dockerfile         # Backend container configuration
│   └── package.json       # Backend dependencies
│
├── selenium-tests/        # Automated test suite
│   ├── src/
│   │   ├── main/java/     # Page Object Models
│   │   └── test/java/     # Test cases (12 tests)
│   ├── pom.xml            # Maven configuration
│   └── testng.xml         # TestNG suite
│
├── docker-compose.yml     # Local development configuration
├── docker-compose-ci.yml  # CI/CD deployment configuration
├── Jenkinsfile            # CI/CD pipeline definition
└── .gitignore            # Git ignore rules
```

## ✅ Prerequisites

Before running this application, ensure you have:

- **Node.js** v18 or higher
- **npm** v9 or higher
- **Docker** v20.10 or higher
- **Docker Compose** v2.x
- **MongoDB Atlas** account (or local MongoDB)
- **Git**

For CI/CD and testing:
- **Jenkins** (for CI/CD pipeline)
- **Java 11+** and **Maven 3.6+** (for Selenium tests)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Esha171/mywebapp-devops.git
cd mywebapp-devops
```

### 2. Backend Setup

```bash
cd backend
npm install

# Create .env file from template
cp .env.template .env
# Edit .env and add your MongoDB URI and JWT secret
```

### 3. Frontend Setup

```bash
cd ../frontend
npm install
```

### 4. Admin Panel Setup

```bash
cd ../admin
npm install
```

## ⚙️ Configuration

### Backend Environment Variables

Create a `.env` file in the `backend/` directory:

```env
MONGO_URI=mongodb+srv://username:password@cluster.mongodb.net/dbname
JWT_SECRET=your_super_secret_jwt_key_here
PORT=4000
```

### Docker Environment Variables

For Docker deployment, set environment variables in your shell or CI/CD system:

```bash
export MONGO_URI="mongodb+srv://username:password@cluster.mongodb.net/dbname"
```

## 🏃 Running the Application

### Option 1: Local Development (without Docker)

**Terminal 1 - Backend:**
```bash
cd backend
npm start
# Backend runs on http://localhost:4000
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm run dev
# Frontend runs on http://localhost:5173
```

**Terminal 3 - Admin:**
```bash
cd admin
npm run dev
# Admin runs on http://localhost:5174
```

### Option 2: Docker Compose (Recommended)

**For local development:**
```bash
docker compose up -d --build
```

**Services will be available at:**
- Frontend: http://localhost:80
- Admin: http://localhost:8080
- Backend API: http://localhost:4000

**To stop:**
```bash
docker compose down
```

### Option 3: CI/CD Docker Compose

```bash
# Ensure MONGO_URI is set as environment variable
export MONGO_URI="your_mongodb_uri"

# Run CI configuration
docker compose -f docker-compose-ci.yml up -d --build
```

**Services will be available at:**
- Frontend: http://localhost:8083
- Admin: http://localhost:8084
- Backend API: http://localhost:4001

## 🔄 CI/CD Pipeline

This project includes a comprehensive Jenkins pipeline that:

### Pipeline Stages:

1. **Checkout** - Fetches code from GitHub
2. **Clean & Free Space** - Cleans up Docker resources
3. **Deploy Backend & All Services** - Builds and deploys containers
4. **Verify Deployment** - Checks container status
5. **Wait for Services** - Ensures services are ready
6. **Run Selenium Tests** - Executes automated tests (12 test cases)

### Pipeline Features:

- ✅ Automatic build on code push
- ✅ Docker image building and deployment
- ✅ Automated Selenium testing with retry mechanism
- ✅ Email notifications to commit author
- ✅ Test reports archiving (JUnit format)
- ✅ Success/Failure email alerts with HTML formatting

### Setting Up Jenkins:

1. **Install Jenkins** on your server (AWS EC2 recommended)
2. **Install Required Plugins:**
   - Docker Pipeline
   - Email Extension Plugin
   - JUnit Plugin
   - Git Plugin

3. **Configure Jenkins:**
   - Add GitHub credentials (PAT)
   - Add MongoDB URI as secret text
   - Configure email SMTP settings

4. **Create Pipeline Job:**
   - Create new Pipeline job
   - Point to repository
   - Use Jenkinsfile from repository

### Running the Pipeline:

```bash
# Push to main branch triggers automatic build
git push origin main
```

## 🧪 Testing

### Automated Selenium Tests

The project includes **12 comprehensive Selenium test cases** covering:

- ✅ Home page loading
- ✅ Pet categories display
- ✅ Navigation between pages
- ✅ Login popup functionality
- ✅ Adopt pets page with filtering
- ✅ Adoption form popup
- ✅ Shopping cart functionality
- ✅ Pet supplies browsing

### Running Tests Locally:

```bash
cd selenium-tests

# Run all tests
mvn clean test

# Run with custom URL
mvn clean test -DBASE_URL=http://localhost:8083
```

### Running Tests in Docker:

```bash
docker run --rm \
  --network host \
  -v $(pwd)/selenium-tests:/app \
  -w /app \
  -e BASE_URL=http://localhost:8083 \
  markhobson/maven-chrome \
  mvn clean test
```

### Test Reports:

After running tests, view reports at:
- `selenium-tests/target/surefire-reports/emailable-report.html`

## 📦 Deployment

### Manual Deployment to AWS EC2:

1. **Launch EC2 Instance** (Ubuntu 22.04 recommended)
2. **Install Docker and Docker Compose**
3. **Clone repository**
4. **Set environment variables**
5. **Run with Docker Compose:**

```bash
# SSH into EC2
ssh -i your-key.pem ubuntu@your-ec2-ip

# Install dependencies
sudo apt update
sudo apt install -y docker.io docker-compose git

# Clone and deploy
git clone https://github.com/Esha171/mywebapp-devops.git
cd mywebapp-devops
export MONGO_URI="your_mongodb_uri"
docker compose -f docker-compose-ci.yml up -d --build
```

### Automated Deployment via Jenkins:

Push to main branch triggers automatic deployment to configured EC2 instance.

## 🔐 Security Notes

- Never commit `.env` files or secrets to version control
- Use environment variables for sensitive data
- JWT secrets should be strong and random
- MongoDB credentials should follow least-privilege principle
- Keep dependencies updated for security patches

## 📝 API Endpoints

### Pet Endpoints
- `GET /api/pet` - List all pets
- `POST /api/pet` - Add new pet (admin)
- `PUT /api/pet/:id` - Update pet (admin)
- `DELETE /api/pet/:id` - Delete pet (admin)

### User Endpoints
- `POST /api/user/register` - User registration
- `POST /api/user/login` - User login
- `GET /api/user/profile` - Get user profile (authenticated)

### Adoption Form Endpoints
- `POST /api/form/submit` - Submit adoption form
- `GET /api/form/list` - List adoption forms (admin)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is part of CSC483 – Topics in Computer Science II (DevOps) course at COMSATS University, Islamabad.

## 👥 Author

**Esha Raza**
- GitHub: [@Esha171](https://github.com/Esha171)
- Repository: [mywebapp-devops](https://github.com/Esha171/mywebapp-devops)

## 🎓 Academic Information

**Course:** CSC483 – Topics in Computer Science II (DevOps)  
**Institution:** COMSATS University, Islamabad  
**Project:** Pet Adoption Web Application with Complete DevOps Pipeline

## 🙏 Acknowledgments

- React and Vite communities
- Docker and Jenkins documentation
- Selenium WebDriver documentation
- TailwindCSS and DaisyUI teams
- MongoDB Atlas

---

**⭐ If you find this project useful, please consider giving it a star!**
