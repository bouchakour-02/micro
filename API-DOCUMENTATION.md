# API Documentation

This document provides detailed information about the REST API endpoints available in our microservices architecture.

## Base URLs

- **API Gateway**: `http://localhost:9090`
- **Product Service**: `http://localhost:8081` (direct access, bypassing gateway)

## Authentication

Most endpoints require authentication via JWT token. To authenticate:

1. Call the authentication endpoint to get a token
2. Include the token in the Authorization header for subsequent requests:
   `Authorization: Bearer <token>`

### Authentication Endpoints

#### Login

```
POST /api/auth/login
```

Request body:
```json
{
  "username": "string",
  "password": "string"
}
```

Response:
```json
{
  "token": "string",
  "username": "string",
  "roles": ["string"]
}
```

## Product Service API

### Get All Products

```
GET /api/products
```

Query parameters:
- `page` (integer, default: 0): Page number
- `size` (integer, default: 10): Page size
- `sort` (string, optional): Sort field (e.g., "name,asc")

Response:
```json
{
  "content": [
    {
      "id": "integer",
      "name": "string",
      "description": "string",
      "price": "number",
      "quantity": "integer",
      "category": "string",
      "createdDate": "string (ISO date)"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": "boolean",
      "unsorted": "boolean"
    },
    "pageNumber": "integer",
    "pageSize": "integer",
    "offset": "integer",
    "paged": "boolean",
    "unpaged": "boolean"
  },
  "totalElements": "integer",
  "totalPages": "integer",
  "last": "boolean",
  "first": "boolean",
  "empty": "boolean"
}
```

### Get Product by ID

```
GET /api/products/{id}
```

Path parameters:
- `id` (integer, required): Product ID

Response:
```json
{
  "id": "integer",
  "name": "string",
  "description": "string",
  "price": "number",
  "quantity": "integer",
  "category": "string",
  "createdDate": "string (ISO date)"
}
```

### Create Product

```
POST /api/products
```

Request body:
```json
{
  "name": "string",
  "description": "string",
  "price": "number",
  "quantity": "integer",
  "category": "string"
}
```

Response:
```json
{
  "id": "integer",
  "name": "string",
  "description": "string",
  "price": "number",
  "quantity": "integer",
  "category": "string",
  "createdDate": "string (ISO date)"
}
```

### Update Product

```
PUT /api/products/{id}
```

Path parameters:
- `id` (integer, required): Product ID

Request body:
```json
{
  "name": "string",
  "description": "string",
  "price": "number",
  "quantity": "integer",
  "category": "string"
}
```

Response:
```json
{
  "id": "integer",
  "name": "string",
  "description": "string",
  "price": "number",
  "quantity": "integer",
  "category": "string",
  "createdDate": "string (ISO date)"
}
```

### Delete Product

```
DELETE /api/products/{id}
```

Path parameters:
- `id` (integer, required): Product ID

Response: HTTP 204 No Content

### Get Products by Category

```
GET /api/products/category/{category}
```

Path parameters:
- `category` (string, required): Category name

Response: Array of product objects

## Weather API

The Weather API integrates with OpenWeatherMap to provide weather information.

### Get Weather by City

```
GET /api/weather/{city}
```

Path parameters:
- `city` (string, required): City name

Response:
```json
{
  "city": "string",
  "country": "string",
  "temperature": "number",
  "description": "string",
  "humidity": "integer",
  "windSpeed": "number",
  "fetchTime": "string (ISO date)"
}
```

## User API

### Get Current User

```
GET /api/users/me
```

Response:
```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "fullName": "string",
  "role": "string",
  "createdDate": "string (ISO date)",
  "lastLogin": "string (ISO date)"
}
```

### Register New User

```
POST /api/users/register
```

Request body:
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "fullName": "string"
}
```

Response:
```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "fullName": "string",
  "role": "string",
  "createdDate": "string (ISO date)"
}
```

## Order API

### Create Order

```
POST /api/orders
```

Request body:
```json
{
  "items": [
    {
      "productId": "integer",
      "quantity": "integer"
    }
  ],
  "shippingAddress": "string",
  "paymentMethod": "string"
}
```

Response:
```json
{
  "id": "integer",
  "userId": "integer",
  "orderDate": "string (ISO date)",
  "status": "string",
  "totalAmount": "number",
  "shippingAddress": "string",
  "paymentMethod": "string",
  "items": [
    {
      "id": "integer",
      "productId": "integer",
      "productName": "string",
      "quantity": "integer",
      "price": "number"
    }
  ]
}
```

### Get User Orders

```
GET /api/orders
```

Response: Array of order objects

### Get Order by ID

```
GET /api/orders/{id}
```

Path parameters:
- `id` (integer, required): Order ID

Response: Order object

## Error Responses

All endpoints may return the following error responses:

### 400 Bad Request

```json
{
  "timestamp": "string (ISO date)",
  "status": 400,
  "error": "Bad Request",
  "message": "string",
  "path": "string"
}
```

### 401 Unauthorized

```json
{
  "timestamp": "string (ISO date)",
  "status": 401,
  "error": "Unauthorized",
  "message": "string",
  "path": "string"
}
```

### 403 Forbidden

```json
{
  "timestamp": "string (ISO date)",
  "status": 403,
  "error": "Forbidden",
  "message": "string",
  "path": "string"
}
```

### 404 Not Found

```json
{
  "timestamp": "string (ISO date)",
  "status": 404,
  "error": "Not Found",
  "message": "string",
  "path": "string"
}
```

### 500 Internal Server Error

```json
{
  "timestamp": "string (ISO date)",
  "status": 500,
  "error": "Internal Server Error",
  "message": "string",
  "path": "string"
}
``` 