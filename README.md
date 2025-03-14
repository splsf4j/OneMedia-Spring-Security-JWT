# REST API with Authentication and External API Integration

## Project Description
This project is a REST API featuring user authentication and registration, as well as integration with an external API to retrieve and store data. Key technologies include Spring Boot, PostgreSQL, JWT, and Spring Data JPA.

## Features
- User registration with data validation and password hashing.
- Authentication using JWT (Access and Refresh tokens).
- Access token renewal with Refresh token.
- Logout with Refresh token invalidation.
- Access to protected routes using Access token.
- Integration with JSONPlaceholder API for fetching and storing posts.

## Technologies Used
- Java + Spring Boot
- PostgreSQL
- Spring Data JPA
- JWT for authentication
- Swagger for API documentation
- Docker for containerization

## Installation and Running the Project

1. Clone the repository:
```bash
git clone https://github.com/your-repo/your-project.git
```
2. Navigate to the project directory:
```bash
cd your-project
```
3. Configure PostgreSQL access in the `application.properties` file.
4. Build the project with Maven:
```bash
./mvnw clean install
```
5. Run the application:
```bash
./mvnw spring-boot:run
```

## Running with Docker

1. Ensure Docker is installed and running.
2. Create a `docker-compose.yml` file with the following content:
```yaml
version: "3.9"

volumes:
  pg_demo:

services:
  demo_db:
    image: postgres
    restart: always
    environment:
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=pass
      - POSTGRES_DB=demo_db
    volumes:
      - pg_demo:/var/lib/postgresql/data
    ports:
      - "127.0.0.1:5433:5432"

  app:
    restart: always
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - demo_db
    environment:
      SPRING_DATASOURCE_URL: "jdbc:postgresql://demo_db:5432/demo_db"
      JAVA_OPTS: "-Xmx512m"
```
3. Run the project with:
```bash
docker-compose up --build
```

## API Endpoints

- User Registration:
```http
POST /auth/register
```
- User Authentication and Token Retrieval:
```http
POST /auth/login
```
- Access Token Renewal:
```http
POST /auth/refresh
```
- Get List of Posts (Protected Route):
```http
GET /posts
```
- Fetch and Save Posts from External API:
```http
GET /external/posts
```

## Project Structure
- **Users Module**: Handles user registration and authentication.
- **Auth Module**: Manages JWT tokens.
- **Posts Module**: Fetches and stores posts from the external API.

## Contact
Project Author: [Vladislav Baryshev]  
Email: [vladislavbaryshev97@gmail.com]  


