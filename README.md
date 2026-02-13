# HexaGlue Demo

A demo application to discover HexaGlue step by step.

## Prerequisites

- Java 17+
- Maven 3.8+

## Project Structure

```
hexaglue-demo/
├── pom.xml                              # Maven + HexaGlue configuration
├── src/main/java/io/hexaglue/demo/
│   ├── DemoApplication.java             # Spring Boot application
│   ├── domain/
│   │   ├── Task.java                    # Aggregate root (contains an intentional violation)
│   │   ├── TaskId.java                  # Value Object (record)
│   │   └── TaskStatus.java              # Enum
│   ├── ports/
│   │   ├── in/TaskUseCases.java         # Driving port
│   │   └── out/TaskRepository.java      # Driven port
│   └── application/
│       └── TaskService.java             # Application service
└── src/test/java/
    └── TaskRepositoryIntegrationTest.java  # Integration tests
```

---

## Tutorial

### Step 1: Clone the project

```bash
git clone https://github.com/hexaglue/hexaglue-demo.git
cd hexaglue-demo
```

### Step 2: Enable Living Documentation

1. In `pom.xml`, uncomment the `hexaglue-plugin-living-doc` dependency in the HexaGlue plugin `<dependencies>` block
2. Run:
   ```bash
   mvn clean compile
   ```
3. Check the generated documentation in `target/hexaglue/reports/living-doc/`

### Step 3: Enable the Audit. The build fails!

1. In `pom.xml`, uncomment the `hexaglue-plugin-audit` dependency
2. Run:
   ```bash
   mvn clean verify -DskipTests
   ```
   The `-DskipTests` option is required because tests would fail: the JPA infrastructure has not been generated yet.
3. **The build fails!** The audit detects an architectural violation:
   - `Task.java` imports `jakarta.persistence.Entity` (domain dependency on infrastructure)

### Step 4: Fix the violation

1. Open `src/main/java/io/hexaglue/demo/domain/Task.java`
2. Remove the `@Entity` annotation from the class
3. Remove the `jakarta.persistence.Entity` import
4. Run again:
   ```bash
   mvn clean verify -DskipTests
   ```
5. **The build passes!** The audit is satisfied.

### Step 5: Generate JPA infrastructure

1. In `pom.xml`, uncomment the `hexaglue-plugin-jpa` dependency
2. Run:
   ```bash
   mvn clean verify
   ```
3. HexaGlue automatically generates the following in `target/hexaglue/generated-sources/`:
   - `TaskEntity.java`: JPA entity
   - `TaskJpaRepository.java`: Spring Data repository
   - `TaskMapper.java`: Domain/entity mapper
   - `TaskRepositoryAdapter.java`: Adapter implementing the driven port

   Generated sources are automatically added to the classpath thanks to `<extensions>true</extensions>`.
4. Integration tests pass and the audit validates the final architecture.

---

## Summary

| Step | Action | Result |
|------|--------|--------|
| 1 | Clone | Project ready |
| 2 | Living Doc | Documentation generated |
| 3 | Audit | Violation detected |
| 4 | Fix | Green build |
| 5 | JPA + Tests | Infrastructure generated, tests OK |

---

## Resources

- [Quick Start Guide](https://hexaglue.io/docs/getting-started/)
- [GitHub HexaGlue](https://github.com/hexaglue/hexaglue)
