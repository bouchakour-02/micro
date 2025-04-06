# Microservice Architecture Project

This project implements a microservice architecture with Spring Boot, Spring Cloud Netflix Eureka for service discovery, and a Spring Cloud Gateway.

## Components

1. **Product Microservice** (Port 8081)
   - Manages product data with CRUD operations
   - Uses H2 in-memory database
   - Swagger UI documentation available
   - Integrates with OpenWeatherMap API
   - Supports pagination for product listings
   - Includes system monitoring endpoints

2. **Discovery Server (Eureka)** (Port 8761)
   - Service registry and discovery

3. **API Gateway** (Port 9090)
   - Routes client requests to appropriate microservices

## External API Integration

This project demonstrates external API consumption by integrating with the OpenWeatherMap API:

1. **Weather API Endpoints**:
   - Get weather by city: `/api/v1/weather/city/{cityName}`
   - Get weather by coordinates: `/api/v1/weather/coordinates?lat={latitude}&lon={longitude}`
   - Get 5-day forecast: `/api/v1/weather/forecast/{cityName}`

2. **Integrated Product-Weather Service**:
   - Get product details with weather information: `/api/v1/product-weather/{productId}?city={cityName}`
   - This showcases how to combine internal data with external API data

## Advanced Features

1. **Pagination**:
   - Get paginated product listings: `/api/v1/products/paged?page=0&size=10&sortBy=name`

2. **System Monitoring**:
   - Health check endpoint: `/api/v1/monitor/health`
   - System information: `/api/v1/monitor/info`

3. **Global Exception Handling**:
   - Consistent error responses across the application
   - Special handling for entity not found and external API errors

4. **Docker Containerization**:
   - Each service is containerized with Docker
   - Services are orchestrated with Docker Compose
   - Environment variables for flexible configuration
   - Networking between containers
   - Health checks to ensure proper startup sequence

## Running with Docker

### Prerequisites
- Docker and Docker Compose installed
- Java 11 (for building)
- Maven (or use the Maven wrapper included)

### Build and Run
1. Use the provided scripts:
   - **Windows**: `docker-build-run.bat`
   - **Linux/Mac**: `bash docker-build-run.sh`

2. Alternatively, run these commands manually:
   ```bash
   # Build the services
   cd discovery-server && ./mvnw clean package -DskipTests && cd ..
   cd microservice && ./mvnw clean package -DskipTests && cd ..
   cd api-gateway && ./mvnw clean package -DskipTests && cd ..
   
   # Start with Docker Compose
   docker-compose up --build
   ```

3. To run in detached mode (background):
   ```bash
   docker-compose up --build -d
   ```

### Viewing Logs
- Use the provided script: `docker-logs.bat` (Windows)
- Or run: `docker-compose logs -f` (all services)
- For individual services: `docker logs -f <service-name>`

### Stopping the Services
```bash
docker-compose down
```

## Manual Deployment (without Docker)

1. Start the Discovery Server first
2. Start the Product Microservice
3. Start the API Gateway

## Auto Git Commit & Push

This project includes scripts for automatically committing and pushing changes to a Git repository.

### Manual Usage

1. Make changes to your code
2. Run `auto-git-commit.bat`
3. If prompted, enter your GitHub repository URL

### Setting Up Scheduled Commits

To set up automatic commits at regular intervals:

1. Run `setup-scheduled-commits.bat`
2. Choose your preferred commit frequency:
   - Hourly
   - Daily
   - Weekly
3. The script will create a Windows scheduled task

### Managing Scheduled Tasks

You can view or modify the scheduled task in Windows Task Scheduler:
1. Open Task Scheduler (search for it in the Start menu)
2. Look for the "AutoGitCommit" task
3. You can disable, modify, or delete the task as needed

## Endpoints

- Eureka Dashboard: http://localhost:8761
- H2 Database Console: http://localhost:8081/h2-console
- Product API (via Gateway): http://localhost:9090/api/v1/products
- Weather API (via Gateway): http://localhost:9090/api/v1/weather/city/{cityName}
- Forecast API (via Gateway): http://localhost:9090/api/v1/weather/forecast/{cityName}
- Integrated API (via Gateway): http://localhost:9090/api/v1/product-weather/{productId}?city={cityName}
- Health Check: http://localhost:9090/api/v1/monitor/health
- System Info: http://localhost:9090/api/v1/monitor/info
- Swagger UI: http://localhost:8081/swagger-ui.html

## Configuration Files

- `application.properties`: Contains configuration for each service
- `pom.xml`: Contains dependencies for each service
- `Dockerfile`: Contains instructions for building Docker images
- `docker-compose.yml`: Orchestrates the multi-container application

## Note

Before setting up scheduled commits, make sure:
1. Git is properly installed and configured
2. You have a GitHub repository created
3. You have the necessary permissions for the repository 