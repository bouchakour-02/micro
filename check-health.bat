@echo off
echo Checking health of all microservices...

echo.
echo ==== Eureka Server Health Check ====
curl -s http://localhost:8761/actuator/health | findstr "UP"
if %ERRORLEVEL% EQU 0 (
    echo Discovery Server: HEALTHY
) else (
    echo Discovery Server: UNHEALTHY
)

echo.
echo ==== Product Service Health Check ====
curl -s http://localhost:8081/actuator/health | findstr "UP"
if %ERRORLEVEL% EQU 0 (
    echo Product Service: HEALTHY
) else (
    echo Product Service: UNHEALTHY
)

echo.
echo ==== API Gateway Health Check ====
curl -s http://localhost:9090/actuator/health | findstr "UP"
if %ERRORLEVEL% EQU 0 (
    echo API Gateway: HEALTHY
) else (
    echo API Gateway: UNHEALTHY
)

echo.
echo ==== Database Health Check ====
docker exec -i mysql mysqladmin -u root -proot ping
if %ERRORLEVEL% EQU 0 (
    echo MySQL Database: HEALTHY
) else (
    echo MySQL Database: UNHEALTHY
)

echo.
echo ==== Monitoring Services Check ====
curl -s http://localhost:9090/-/healthy
if %ERRORLEVEL% EQU 0 (
    echo Prometheus: HEALTHY
) else (
    echo Prometheus: UNHEALTHY
)

echo.
echo Health check completed.
pause 