@echo off
echo Building and running microservices with Docker...

echo Building Discovery Server...
cd discovery-server
call mvnw clean package -DskipTests
cd ..

echo Building Product Microservice...
cd microservice
call mvnw clean package -DskipTests
cd ..

echo Building API Gateway...
cd api-gateway
call mvnw clean package -DskipTests
cd ..

echo Building and starting Docker containers...
docker-compose up --build

rem To run in detached mode, uncomment the following line:
rem docker-compose up --build -d

pause 