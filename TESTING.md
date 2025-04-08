# Insurance Recommendation Service Testing Guide

This guide covers the steps to thoroughly test the Insurance Recommendation Service.

## Prerequisites

Before testing, make sure you have:

- Docker Desktop installed and running
- cURL installed (for command-line tests)
- Postman (optional, for API testing)

## Testing Steps

### 1. Start the Services

There are two ways to start the services:

**Development Mode**:
```bash
docker-compose up -d
```

**Production Mode** (includes MySQL, Prometheus, and Grafana):
```bash
docker-compose -f docker-compose.prod.yml up -d
```

Wait approximately 30 seconds for all services to initialize properly.

### 2. Verify Service Registration

Check that services are registered with Eureka:

1. Open a browser and go to: http://localhost:8761
2. Verify that you see the following services registered:
   - API-GATEWAY
   - INSURANCE-RECOMMENDATION-SERVICE

### 3. Testing Recommendation Service API

#### Submit Insurance Preferences

**HTTP Request**:
- Method: POST
- URL: http://localhost:9090/api/preferences
- Headers: Content-Type: application/json
- Body:
```json
{
  "userEmail": "test@example.com",
  "age": 35,
  "annualIncome": 75000,
  "hasFamilyDependents": true,
  "hasHealthIssues": false,
  "hasVehicle": true,
  "vehicleType": "Car",
  "hasProperty": true,
  "propertyType": "House",
  "interestedInLifeInsurance": true,
  "interestedInHealthInsurance": true,
  "interestedInVehicleInsurance": true,
  "interestedInPropertyInsurance": true
}
```

**Expected Response**:
- Status: 200 OK
- Body: JSON containing the saved preference with an ID

#### Get Recommendations for User

**HTTP Request**:
- Method: GET
- URL: http://localhost:9090/api/preferences/user/test@example.com/recommendations

**Expected Response**:
- Status: 200 OK
- Body: JSON array containing insurance recommendations, with scores and types

#### Get Recommendations by Preference ID
(Use the ID obtained from the first request)

**HTTP Request**:
- Method: GET
- URL: http://localhost:9090/api/preferences/{preference_id}/recommendations

**Expected Response**:
- Status: 200 OK
- Body: JSON array containing insurance recommendations

### 4. Testing Admin API for Recommendation Rules

#### Get All Rules

**HTTP Request**:
- Method: GET
- URL: http://localhost:9090/api/admin/rules

**Expected Response**:
- Status: 200 OK
- Body: JSON array of recommendation rules

#### Create a New Rule

**HTTP Request**:
- Method: POST
- URL: http://localhost:9090/api/admin/rules
- Headers: Content-Type: application/json
- Body:
```json
{
  "ruleName": "Test Rule",
  "description": "A test rule",
  "insuranceType": "LIFE",
  "criteria": "age > 40",
  "weight": 25,
  "isActive": true
}
```

**Expected Response**:
- Status: 200 OK
- Body: JSON of the created rule with an ID

#### Update a Rule
(Use the ID obtained from the previous request)

**HTTP Request**:
- Method: PUT
- URL: http://localhost:9090/api/admin/rules/{rule_id}
- Headers: Content-Type: application/json
- Body:
```json
{
  "ruleName": "Updated Test Rule",
  "description": "An updated test rule",
  "insuranceType": "LIFE",
  "criteria": "age > 45",
  "weight": 30,
  "isActive": true
}
```

**Expected Response**:
- Status: 200 OK
- Body: JSON of the updated rule

#### Activate/Deactivate a Rule
(Use the ID obtained from the first rule request)

**HTTP Request**:
- Method: PUT
- URL: http://localhost:9090/api/admin/rules/{rule_id}/deactivate

**Expected Response**:
- Status: 200 OK
- Body: JSON of the rule with isActive set to false

### 5. Testing End-to-End Flow

1. Submit preference for a user
2. Check recommendations generated for the user
3. Create a new recommendation rule that would apply to the user
4. Submit a new preference for the same user
5. Verify that the new recommendations reflect the new rule

### 6. Monitoring (Production Mode Only)

#### Check Prometheus

1. Open a browser and go to: http://localhost:9090
2. Navigate to the "Targets" page
3. Verify all targets are "UP"
4. Run a query to check API request metrics: `http_server_requests_seconds_count`

#### Check Grafana

1. Open a browser and go to: http://localhost:3000
2. Login with admin/admin
3. Import a dashboard for Spring Boot metrics
4. Verify metrics are being collected

## Automated Testing Script

Use the provided `test-recommendations.bat` script for quick testing:
```bash
./test-recommendations.bat
```

## Clean Up

When testing is complete, bring down all services:

```bash
docker-compose down
```

For production mode:
```bash
docker-compose -f docker-compose.prod.yml down
```