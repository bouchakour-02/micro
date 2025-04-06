@echo off
echo Docker container logs viewer

echo Available containers:
echo 1. discovery-server
echo 2. product-service
echo 3. api-gateway
echo 4. All services

set /p container="Select container (1-4): "

if "%container%"=="1" (
    docker logs -f discovery-server
) else if "%container%"=="2" (
    docker logs -f product-service
) else if "%container%"=="3" (
    docker logs -f api-gateway
) else if "%container%"=="4" (
    docker-compose logs -f
) else (
    echo Invalid selection
)

pause 