@echo off
echo Docker Service Scaling Tool

echo Available services:
echo 1. product-service
echo 2. api-gateway

set /p service="Select service to scale (1-2): "
set service_name=

if "%service%"=="1" (
    set service_name=product-service
) else if "%service%"=="2" (
    set service_name=api-gateway
) else (
    echo Invalid selection
    goto end
)

set /p instances="How many instances do you want to run? "

if defined service_name (
    echo Scaling %service_name% to %instances% instances...
    docker-compose up -d --scale %service_name%=%instances%
    echo Service scaled successfully!
)

:end
pause 