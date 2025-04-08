@echo off
echo ====== Insurance Recommendation Service Testing ======
echo.

echo Step 1: Building and starting all services
echo -----------------------------------------------
echo This will build and start all required services:
echo - Discovery Server
echo - API Gateway
echo - Recommendation Service
echo - MySQL Database (if in production mode)
echo.
echo Running docker-compose up -d
docker-compose up -d
IF %ERRORLEVEL% NEQ 0 (
  echo Error starting services. Make sure Docker Desktop is running.
  exit /b 1
)
echo.
echo Waiting for services to start (30 seconds)...
timeout /t 30 /nobreak

echo.
echo Step 2: Verifying Service Registration
echo -----------------------------------------------
echo Checking if services are registered with Eureka
echo.
curl -s http://localhost:8761/eureka/apps
echo.
echo.

echo Step 3: Testing Recommendation API Endpoints
echo -----------------------------------------------
echo.
echo 3.1: Creating a test insurance preference...
echo.
curl -X POST http://localhost:9090/api/preferences -H "Content-Type: application/json" -d "{\"userEmail\":\"test@example.com\",\"age\":35,\"annualIncome\":75000,\"hasFamilyDependents\":true,\"hasHealthIssues\":false,\"hasVehicle\":true,\"vehicleType\":\"Car\",\"hasProperty\":true,\"propertyType\":\"House\",\"interestedInLifeInsurance\":true,\"interestedInHealthInsurance\":true,\"interestedInVehicleInsurance\":true,\"interestedInPropertyInsurance\":true}"
echo.
echo If successful, you should see the created preference with an ID above
echo.
echo 3.2: Waiting for recommendations to be generated (5 seconds)...
timeout /t 5 /nobreak
echo.
echo 3.3: Retrieving recommendations for the user...
echo.
curl -X GET http://localhost:9090/api/preferences/user/test@example.com/recommendations
echo.
echo If successful, you should see a list of personalized insurance recommendations
echo.

echo Step 4: Testing Admin API for Recommendation Rules
echo -----------------------------------------------
echo.
echo 4.1: Retrieving all recommendation rules...
echo.
curl -X GET http://localhost:9090/api/admin/rules
echo.
echo If successful, you should see a list of recommendation rules
echo.
echo 4.2: Creating a new recommendation rule...
echo.
curl -X POST http://localhost:9090/api/admin/rules -H "Content-Type: application/json" -d "{\"ruleName\":\"Test Rule\",\"description\":\"A test rule\",\"insuranceType\":\"LIFE\",\"criteria\":\"age > 40\",\"weight\":25,\"isActive\":true}"
echo.
echo If successful, you should see the created rule with an ID above
echo.

echo.
echo ====== Testing Complete ======
echo.
echo To stop all services, run: docker-compose down
echo.
pause 