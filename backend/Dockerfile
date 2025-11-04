# backend/Dockerfile
FROM node:18-alpine

# Install required build tools for bcrypt
RUN apk add --no-cache python3 make g++

WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./

# Install all dependencies including bcrypt properly
RUN npm ci --omit=dev || npm install

# Copy source code
COPY . .

# Expose backend port
EXPOSE 4000

# Start backend server
CMD ["npm", "start"]
