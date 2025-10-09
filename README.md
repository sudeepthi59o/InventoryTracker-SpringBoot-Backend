### Backend README (Spring Boot)

# Inventory Tracker - Backend

## Overview
This is the **backend** part of the **Inventory Tracker** application, built with **Spring Boot**. It serves as the API that handles requests related to inventory management, such as adding, updating, deleting, and retrieving inventory items.

### Features:
- Provides RESTful API endpoints for managing inventory.
- Connects to a PostgreSQL database for persistent data storage.
- Uses JWT for authentication and authorization.

## Technologies Used:
- **Spring Boot**: Framework for building Java-based backend applications.
- **PostgreSQL**: Relational database for storing inventory data.
- **JPA/Hibernate**: ORM framework for database interaction.
- **JWT**: For handling authentication.

## Setup & Installation:

1. Clone the repository:
    ```bash
    git clone <repo-url>
    cd repo-name
    ```

2. Set up database configuration in `application.yml` or environment variables:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://<db-url>:5432/<db-name>>
        username: <db-user>
        password: <db-password>
    ```

3. Build and run the application:
    ```bash
    mvn spring-boot:run
    ```

4. The API will be available at `http://localhost:8080`.

### Deployment
- The backend is hosted on **AWS EC2** and connected to an **RDS** PostgreSQL database.
- The JAR file is deployed using a **CI/CD pipeline**.
