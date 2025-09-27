# Calculator API Project

This project is a Spring Boot microservice system that performs basic calculator operations via Kafka messaging. It includes two main services and one shared module:

- **Calculator Service**: Consumes calculation requests, performs operations, and publishes results.
- **REST Service**: Exposes HTTP endpoints and communicates with the Calculator service using Kafka request-reply.
- **Shared**: Contains classes with Data Transfer Objects (DTOs) used by both services 
---

# Running with Docker Compose
`docker compose up --build`

# Stop all services
`docker compose down`

# REST API Endpoints
The API supports 4 different operations: `sum, subtraction, division, multiplication`

### Example request:

GET http://localhost:8081/calculate/sum?a=10&b=5

### Example Response:
`{
"result": 15,
"success": true
}`

# Extras

Log4j is implemented and used for output event and errors and JaCoCo is used for test coverage


