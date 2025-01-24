# Intellexi Fullstack Coding Assignment

## Overview
This project implements a trail race application system using a microservice architecture with CQRS principles. It includes the following components:
- REST APIs for managing applications and races.
- A frontend web application for both administrators and applicants.
- Messaging between services via RabbitMQ.

## Prerequisites
- Docker and Docker Compose installed on your system.

## Running the Project
1. Navigate to the root directory of the project.
2. Execute the following commands to build and run the project:
   ```bash
   docker-compose build
   docker-compose up
3. Access the web application on http://localhost:4200.

## User Credentials
Use the following credentials to log in and explore the system:

| Email                   | Password | Role       |
|-------------------------|----------|------------|
| john.doe@example.com    | password | Admin      |
| jane.smith@example.com  | password | Applicant  |
| alice.brown@example.com | password | Applicant  |

## RabbitMQ
The project uses RabbitMQ for messaging between services. You can access the RabbitMQ management dashboard at http://localhost:15672 using the following credentials:
- Username: guest
- Password: guest

## Next Steps
Due to the time limit, I wasn't able to implement the following improvements, but these are my next steps for the project:
### 1. Testing
Add unit and integration tests to ensure the reliability and robustness of the system.
### 2. Configuration Management
Introduce proper configuration management using environment variables to redefine configuration values easily.
### 3. Versioning
Introduce API versioning to support backward compatibility and smooth transitions when updating endpoints.
### 4. Design Improvements
Consider improving the frontend design for a better user experience (UI/UX).
Ensure accessibility standards are met to make the application more inclusive.
### 5. Pagination and Filtering
Enhance the APIs by implementing pagination and filtering capabilities, especially for endpoints that return lists (e.g., races or applications).
### 6. Handling Long Strings
Implement safeguards to gracefully handle input strings exceeding 255 characters.