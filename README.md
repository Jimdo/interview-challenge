# Interview Challenge

A Spring Boot backend service for managing websites, their versions, and domain mappings.

## Tech Stack

- Kotlin 2.3.10
- Spring Boot 4.0.3
- PostgreSQL 16
- Gradle 9.3.1
- Java 21

## Architecture

The project follows a hexagonal (ports & adapters) architecture:

```
core/                          # Business logic, no framework dependencies
├── {entity}/model/            # Domain models (pure data classes)
├── {entity}/port/             # Port interfaces (driven side contracts)
└── {entity}/usecase/          # Use cases (application services)

adapter/                       # Infrastructure implementations
├── {entity}/controller/       # REST controllers (driving side)
│   ├── mapper/                # Domain <-> response mapping
│   └── rest/                  # Request/response DTOs
└── {entity}/persistence/      # Database adapters (driven side)
    ├── entity/                # JPA entities
    ├── repository/            # Spring Data repositories
    ├── mapper/                # Entity <-> domain mapping
    └── impl/                  # Port implementations
```

Key patterns:
- **Arrow Either** for functional error handling (`Either<Error, Success>`)
- **Extension functions** for stateless mappers at adapter boundaries
- **Sealed classes** for type-safe API responses
- **ArchUnit** tests enforce layer dependency rules

## Domain Model

- **User** - application user (id, email)
- **Website** - belongs to a user (id, user_id)
- **WebsiteVersion** - versioned content snapshots of a website (website_id, version_number, payload)
- **Domain** - domain name mapped to a website (domain, website_id)

## Prerequisites

- Docker (at least 4 GB memory allocated to the Docker VM)

## Getting Started

```sh
docker compose up --build
```

The frontend is available at `http://localhost:3000`. The API is available at `http://localhost:8080`. Swagger UI is at `http://localhost:8080/docs/swagger-ui.html`.

## Running Tests

```sh
./gradlew test
```

Tests use Testcontainers to spin up a PostgreSQL instance automatically — no running database required.

## API Endpoints

| Method | Path                | Description        |
|--------|---------------------|--------------------|
| GET    | /api/users          | List users         |
| GET    | /api/users/{id}     | Get user by ID     |
| POST   | /api/users          | Create a user      |
| DELETE | /api/users/{id}     | Delete a user      |
| GET    | /api/websites       | List websites      |
| GET    | /api/websites/{id}  | Get website by ID  |
| POST   | /api/websites       | Create a website   |
| DELETE | /api/websites/{id}  | Delete a website   |

## Frontend

A React SPA that consumes the backend API. Located in the `frontend/` directory.

**Stack:** Vite + React 19 + TypeScript + SCSS Modules + React Router 7

### Setup

```sh
cd frontend
npm install
```

### Development

```sh
npm run dev
```

Starts on `http://localhost:3000`. The Vite dev server proxies `/api` requests to `http://localhost:8080`, so make sure the backend is running.

### Build

```sh
npm run build
```

Output goes to `frontend/dist/`.
