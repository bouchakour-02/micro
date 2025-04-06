@echo off
echo Building and deploying production environment...

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

echo Building and starting Docker containers in production mode...
docker-compose -f docker-compose.prod.yml up --build -d

echo Production environment started successfully!
echo Eureka: http://localhost:8761
echo API Gateway: http://localhost:9090
echo Prometheus: http://localhost:9090
echo Grafana: http://localhost:3000 (admin/admin)

pause 