@echo off
echo Building and deploying production environment...

echo Starting Docker Compose with production configuration...
docker compose -f docker-compose.prod.yml up -d --build

echo Production environment started successfully!
echo Eureka: http://localhost:8761
echo API Gateway: http://localhost:9090
echo Prometheus: http://localhost:9090
echo Grafana: http://localhost:3000 (admin/admin)
pause 