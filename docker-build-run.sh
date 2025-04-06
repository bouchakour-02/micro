#!/bin/bash

# Build the Maven projects
echo "Building Discovery Server..."
cd discovery-server && ./mvnw clean package -DskipTests
cd ..

echo "Building Product Microservice..."
cd microservice && ./mvnw clean package -DskipTests
cd ..

echo "Building API Gateway..."
cd api-gateway && ./mvnw clean package -DskipTests
cd ..

# Build and run Docker Compose services
echo "Building and starting Docker containers..."
docker-compose up --build

# To run in detached mode, use the following command instead:
# docker-compose up --build -d 