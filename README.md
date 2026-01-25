# HexaGlue Demo

Application de démonstration pour découvrir HexaGlue pas à pas.

## Prérequis

- Java 17+
- Maven 3.8+
- HexaGlue 5.0.0-SNAPSHOT installé localement (`mvn install` dans le repo hexaglue)

## Structure du projet

```
hexaglue-demo/
├── pom.xml                              # Configuration Maven + HexaGlue (commentée)
├── src/main/java/io/hexaglue/demo/
│   ├── DemoApplication.java             # Application Spring Boot
│   ├── domain/
│   │   ├── Task.java                    # Agrégat racine (⚠️ contient une violation)
│   │   ├── TaskId.java                  # Value Object
│   │   └── TaskStatus.java              # Enum
│   ├── ports/
│   │   ├── in/TaskUseCases.java         # Port driving
│   │   └── out/TaskRepository.java      # Port driven
│   └── application/
│       └── TaskService.java             # Service applicatif
└── src/test/java/
    └── TaskRepositoryIntegrationTest.java  # Tests d'intégration
```

---

## Tutoriel

### Étape 1 : Générer la Living Documentation

1. Dans `pom.xml`, décommentez le plugin HexaGlue et le plugin `hexaglue-plugin-living-doc`
2. Lancez :
   ```bash
   mvn clean compile
   ```
3. Consultez la documentation générée dans `target/hexaglue/reports/living-doc/`

### Étape 2 : Activer l'Audit d'Architecture

1. Dans `pom.xml`, décommentez le plugin `hexaglue-plugin-audit`
2. Lancez :
   ```bash
   mvn clean verify
   ```
3. **Le build échoue !** L'audit détecte une violation :
   - `Task.java` importe `jakarta.persistence.Entity` (dépendance domaine → infrastructure)

### Étape 3 : Corriger la Violation

1. Ouvrez `src/main/java/io/hexaglue/demo/domain/Task.java`
2. Supprimez l'import :
   ```java
   import jakarta.persistence.Entity;
   ```
3. Relancez :
   ```bash
   mvn clean verify
   ```
4. **Le build passe !** L'audit est satisfait.

### Étape 4 : Générer l'Infrastructure JPA

1. Dans `pom.xml`, décommentez le plugin `hexaglue-plugin-jpa`
2. Lancez :
   ```bash
   mvn clean compile
   ```
3. Consultez le code généré dans `target/generated-sources/hexaglue/`
   - `TaskEntity.java` : Entité JPA
   - `TaskJpaRepository.java` : Repository Spring Data
   - `TaskRepositoryAdapter.java` : Adapter implémentant le port

### Étape 5 : Vérifier avec les Tests

1. Lancez les tests d'intégration :
   ```bash
   mvn test
   ```
2. Les tests écrivent et lisent dans la base H2 via l'infrastructure générée.

### Étape 6 : Démarrer l'Application

```bash
mvn spring-boot:run
```

L'application démarre avec une base H2 en mémoire.
Console H2 disponible sur : http://localhost:8080/h2-console

---

## Résumé

| Étape | Action | Résultat |
|-------|--------|----------|
| 1 | Living Doc | Documentation générée |
| 2 | Audit | Violation détectée |
| 3 | Correction | Build vert |
| 4 | JPA Generation | Infrastructure générée |
| 5 | Tests | Persistence fonctionnelle |
| 6 | Run | Application démarrée |

---

## Ressources

- [Documentation HexaGlue](https://hexaglue.io/getting-started/)
- [GitHub HexaGlue](https://github.com/hexaglue/hexaglue)
