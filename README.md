# Microservice Assurance Platform

A comprehensive microservices-based insurance platform with Docker containerization, service discovery, API gateway, and monitoring capabilities.

## Architecture Overview

This platform is built using a microservices architecture with the following components:

- **Service Discovery (Eureka)**: Central registry for all microservices
- **API Gateway**: Single entry point for client requests
- **Insurance Recommendation Service**: Core business logic for insurance recommendations
- **MySQL Database**: Persistent data storage
- **Prometheus & Grafana**: Monitoring and metrics visualization

## Technologies Used

- Java 11
- Spring Boot 2.7.x
- Spring Cloud (Eureka, Gateway)
- Docker & Docker Compose
- MySQL 8.0
- Prometheus & Grafana
- Maven

## Getting Started

### Prerequisites

- Docker Desktop
- JDK 11+
- Maven 3.6+

### Production Deployment

To deploy the application in production mode:

```bash
# Run the production deployment script
./docker-deploy-prod.bat
```

This will:
1. Build all services with multi-stage Docker builds
2. Deploy all containers with proper configuration
3. Set up monitoring with Prometheus and Grafana

### Development Setup

For local development:

```bash
# Build and run in development mode
./docker-build-run.bat
```

### Access Points

- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:9090
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (admin/admin)

## API Endpoints

The Insurance Recommendation Service provides the following API endpoints:

### Submit Insurance Preferences
- **URL**: `/api/preferences`
- **Method**: POST
- **Description**: Submit user preferences for insurance recommendations
- **Request Body**: Insurance preference details (age, income, interests, etc.)
- **Response**: Saved preference with ID

### Get Recommendations by Preference ID
- **URL**: `/api/preferences/{id}/recommendations`
- **Method**: GET
- **Description**: Get recommendations based on a specific preference submission
- **Response**: List of personalized insurance recommendations with scores

### Get User Recommendations
- **URL**: `/api/preferences/user/{email}/recommendations`
- **Method**: GET
- **Description**: Get the latest recommendations for a specific user
- **Response**: List of personalized insurance recommendations with scores

## Monitoring Setup

The platform includes a comprehensive monitoring solution:

- Prometheus collects metrics from all services
- Grafana provides dashboards for visualization
- All services expose metrics through Spring Boot Actuator

## Production Scaling

To scale services in production:

```bash
# Scale microservices horizontally
./docker-scale.bat
```

## Configuration

Environment-specific configuration is managed through:
- Docker Compose environment variables
- Spring profiles (dev, prod)
- External configuration servers

## Contributing

1. Fork the repository
2. Create a feature branch
3. Submit a pull request

## License

This project is licensed under the MIT License.