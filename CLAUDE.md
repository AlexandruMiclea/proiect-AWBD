# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvn clean install

# Run (auto-starts PostgreSQL via Docker Compose)
mvn spring-boot:run

# Package
mvn clean package

# Run tests
mvn test
```

## Architecture

Standard Spring Boot 3-layer REST API for a petrol station management system.

**Stack:** Java 21, Spring Boot 4.0.3, PostgreSQL 16.3, Spring Data JPA, MapStruct 1.6.3, Lombok, Jakarta Validation

**Package:** `org.alexmiclea.reptopetrol`

**Layers:**
- `controller/` — REST endpoints under `/api/{resource}`, all follow CRUD pattern (GET all/by-id, POST add/bulkAdd, PUT update, DELETE)
- `service/` — Business logic; uses mappers for DTO↔entity conversion
- `repository/` — Spring Data JPA; all extend `JpaRepository<Entity, UUID>`
- `mapper/` — MapStruct interfaces (`@Mapper(componentModel = "spring")`); generated at compile time
- `dto/` — API contracts mirroring entity structure
- `model/` — JPA entities; `model/composites/` for entities with composite keys (Inventory, FuelSupply), `model/composites/keys/` for `@Embeddable` key classes

**Domain entities:** Employee, Fuel, Product, Store, Station, Supplier, Transport, Contract, Inventory (composite), FuelSupply (composite)

**Enums:** `FuelType` (GAS/DIESEL/LPG), `EmployeeRole` (CASHIER/ATTENDANT)

## Database

PostgreSQL runs via Docker Compose (`compose.yaml`). Spring Boot auto-starts it with `spring.docker.compose.enabled=true`. Schema is managed by Hibernate DDL `update` — no migration tool.

- DB: `reptopetrol`, user/pass: `reptopetrol/reptopetrol`, port `5432`
- PgAdmin available at `localhost:15432` (alex@pg.admin / password)

## Key Conventions

- **Constructor injection** via Lombok `@RequiredArgsConstructor` — do not use field injection
- **MapStruct processor** must be listed after Lombok in the Maven compiler annotation processor path (order matters — see `pom.xml`)
- **Composite entities** (Inventory, FuelSupply) use `@EmbeddedId` with a separate `@Embeddable` key class in `model/composites/keys/`
- Validation annotations appear on both entities and DTOs