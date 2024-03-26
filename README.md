# Auth Service
This repository contains the auth microservice codebase, responsible for:
- login
- logout
- register

## Run 

```bash
# Choose the desired profile: prod, dev, or local
export SPRING_PROFILES_ACTIVE=prod 

# Clean the project
mvn clean

# Run the application
mvn spring-boot:run
```

## Run with Docker
```bash
docker-compose up
```

## Run tests
```bash
mvn test
