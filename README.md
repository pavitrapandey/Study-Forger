# Study Forger – Smart Study Scheduler

Study Forger is a backend for a modular study management system that helps students organize subjects, track topics, and master revision using the SM-2 Spaced Repetition Algorithm. It is designed around clean REST APIs, role-based access, and pragmatic functionality.

---

## Tech Stack

- Java 21, Spring Boot 3.5.x
- MySQL 8, JPA/Hibernate
- Spring Security (JWT + Refresh Tokens), JJWT 0.12.6
- ModelMapper, Jakarta Validation
- OpenAPI/Swagger via springdoc-openapi 2.x
- Maven

---

## Features (Current)

- Authentication & Authorization
  - JWT access tokens + refresh tokens
  - Role-based access control (ROLE_ADMIN, ROLE_NORMAL)
- User Module
  - Create user (registration), update, delete, get by ID, get by email
  - Upload/serve/delete profile image
- Subject Module
  - Create/update/delete subjects under a user
  - List user subjects with pagination
  - Search subjects by name
- Topic Module
  - Create/update/delete topics under a subject
  - List topics by subject (paginated)
  - Search topics by name/difficulty
  - List topics by user
- Revision Module (SM-2 Based)
  - Review a topic and update scheduling state
  - List due topics for a user
  - List all revision topics for a user
- Dashboard Module
  - Aggregated metrics for a user

---

## Authentication Flow

- Generate token
  - POST /api/auth/generate-token
  - Body: { "username": "...", "password": "..." }
  - Response includes: JWT access token and refreshToken object
- Regenerate token (refresh)
  - POST /api/auth/regenerate-token
  - Body: { "refreshToken": "..." }
  - Response includes: new JWT access token
- Use the access token for protected APIs:
  - Authorization: Bearer <jwt>

Swagger UI is pre-configured with a Bearer JWT security scheme.

---

## API Endpoints (Summary)

Public (no auth):
- GET /swagger-ui/**, /swagger-ui.html, /swagger-resources/**, /webjars/**, /v2/api-docs, /v3/api-docs/**
- POST /api/auth/generate-token
- POST /api/auth/regenerate-token

Users (base: /api/users)
- POST /api/users (register) [public]
- GET /api/users/{userId} [ROLE_ADMIN]
- GET /api/users (paginated) [ROLE_ADMIN]
- GET /api/users/email/{email} [ROLE_ADMIN]
- PUT /api/users/{userId} [ROLE_NORMAL or ROLE_ADMIN]
- DELETE /api/users/{userId} [ROLE_ADMIN]
- Images:
  - POST /api/users/image/{userId} [ROLE_NORMAL]
  - GET /api/users/image/{userId} [ROLE_NORMAL]
  - DELETE /api/users/image/{userId} [ROLE_NORMAL]

Subjects (base: /api/users/{user_id}/subjects)
- POST /api/users/{user_id}/subjects [ROLE_NORMAL]
- PUT /api/users/{user_id}/subjects/{subject_id} [ROLE_NORMAL] (must be creator)
- GET /api/users/{user_id}/subjects/{subject_id} [ROLE_NORMAL]
- GET /api/users/{user_id}/subjects [ROLE_NORMAL, paginated]
- DELETE /api/users/{user_id}/subjects/{subject_id} [ROLE_NORMAL] (must be creator)
- GET /api/users/{user_id}/subjects/search?subjectName=... [ROLE_NORMAL]

Topics (base prefix from controller: /api)
- POST /api/subject/{subject_id}/topics [ROLE_NORMAL]
- PUT /api/subject/{subject_id}/topics/{topic_id} [ROLE_NORMAL]
- GET /api/subject/{subject_id}/topics/{topic_id} [ROLE_NORMAL]
- GET /api/subject/{subject_id}/topics [ROLE_NORMAL, paginated]
- GET /api/subject/{subject_id}/topics/search?topicName=... [ROLE_NORMAL]
- GET /api/subject/{subject_id}/topics/difficulty?difficulty=... [ROLE_NORMAL]
- DELETE /api/subject/{subject_id}/topics/{topic_id} [ROLE_NORMAL]
- GET /api/user/{userId}/topic [ROLE_NORMAL]

Revision (base: /api/revision)
- PUT /api/revision (review a topic) [ROLE_NORMAL]
- GET /api/revision/due/{userId} [ROLE_NORMAL]
- GET /api/revision/all/{userId} [ROLE_NORMAL]

Dashboard (base: /api/dashboard)
- GET /api/dashboard/{userId} [ROLE_NORMAL]

Swagger & OpenAPI
- Swagger UI: /swagger-ui/index.html
- OpenAPI JSON: /v3/api-docs

---

## Setup & Run

Prerequisites
- JDK 21
- MySQL 8
- Maven

Database
- Create database: study_forge
- Configure credentials in src/main/resources/application.properties

Key application.properties (defaults)
- spring.datasource.url=jdbc:mysql://localhost:3306/study_forge
- spring.datasource.username=YOUR_USERNAME
- spring.datasource.password=YOUR_PASSWORD
- spring.jpa.hibernate.ddl-auto=update
- user.profile.image.path=images/users/
- CORS origin is set to http://localhost:5173 (AppConstants)

Run (development)
- mvn spring-boot:run

Build a jar
- mvn clean package
- java -jar target/Study-Forger-0.0.1-SNAPSHOT.jar

Swagger UI
- http://localhost:8080/swagger-ui/index.html

Image Storage
- Uploaded user images are stored under images/users/ (relative to the working directory). Ensure the directory exists and the app has write permissions.

---

## Project Structure (High-Level)

- Configuration: AppConfig (ModelMapper), AppConstants, SwaggerConfig
- Security: SecurityConfig, JWT helper/filter/entrypoint/controller, RefreshToken service/repo
- User: entity, DTO, repository, service, controller, image endpoints
- Subject: entity, DTO, repository, service, controller
- Topic: entity, DTOs, repository, service, controller
- Revision: entity, DTOs, repository, service, controller (SM-2 scheduling)
- Dashboard: service, controller
- Files: FileService + ImageResponse
- Exceptions: custom exceptions for robust error reporting

---

## Notes and Known Discrepancies

- Security path vs controller path
  - TopicController uses /api/subject/... while SecurityConfig protects /api/subjects/... (plural). Consider aligning.
  - RevisionController uses /api/revision/... while SecurityConfig protects /api/revisions/** (plural). Consider aligning.
- HTTP status codes
  - Some TopicController GET endpoints return 302 FOUND; 200 OK is more appropriate for successful reads.
- HealthCheck controller
  - Returns view names because it's annotated with @Controller and returns raw strings. Use @RestController or @ResponseBody if intended as test endpoints.
- Refresh token verify/delete
  - Prefer deleting by the persisted entity (query by token, then delete) rather than mapping DTO to entity.
- Debug/preview
  - Security debug logging and @EnableWebSecurity(debug = true) are enabled; disable in production.
  - Maven compiler uses --enable-preview; remove if not using preview features.
- Configuration
  - Do not commit production credentials; use profiles/environment variables.

---

## Roadmap

- Notification system (email/push) for due reviews
- Frontend (React) dashboard and workflows
- Production hardening (logging, metrics, rate limiting)
- CI/CD and containerization

---

## Contributing

Issues and pull requests are welcome. Please open a ticket describing the change or problem before submitting PRs.
