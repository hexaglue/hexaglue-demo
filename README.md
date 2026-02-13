# HexaGlue Demo

Application de demonstration pour decouvrir HexaGlue pas a pas.

## Prerequis

- Java 17+
- Maven 3.8+

## Structure du projet

```
hexaglue-demo/
├── pom.xml                              # Configuration Maven + HexaGlue
├── src/main/java/io/hexaglue/demo/
│   ├── DemoApplication.java             # Application Spring Boot
│   ├── domain/
│   │   ├── Task.java                    # Agregat racine (contient une violation intentionnelle)
│   │   ├── TaskId.java                  # Value Object (record)
│   │   └── TaskStatus.java              # Enum
│   ├── ports/
│   │   ├── in/TaskUseCases.java         # Port driving
│   │   └── out/TaskRepository.java      # Port driven
│   └── application/
│       └── TaskService.java             # Service applicatif
└── src/test/java/
    └── TaskRepositoryIntegrationTest.java  # Tests d'integration
```

---

## Tutoriel

### Etape 1 : Cloner le projet

```bash
git clone https://github.com/hexaglue/hexaglue-demo.git
cd hexaglue-demo
```

### Etape 2 : Activer la Living Documentation

1. Dans `pom.xml`, decommentez la dependance `hexaglue-plugin-living-doc` dans le bloc `<dependencies>` du plugin HexaGlue
2. Lancez :
   ```bash
   mvn clean compile
   ```
3. Consultez la documentation generee dans `target/hexaglue/reports/living-doc/`

### Etape 3 : Activer l'Audit. Le build echoue !

1. Dans `pom.xml`, decommentez la dependance `hexaglue-plugin-audit`
2. Lancez :
   ```bash
   mvn clean verify -DskipTests
   ```
   L'option `-DskipTests` est necessaire car les tests echoueraient : l'infrastructure JPA n'est pas encore generee.
3. **Le build echoue !** L'audit detecte une violation architecturale :
   - `Task.java` importe `jakarta.persistence.Entity` (dependance domaine vers infrastructure)

### Etape 4 : Corriger la violation

1. Ouvrez `src/main/java/io/hexaglue/demo/domain/Task.java`
2. Supprimez l'annotation `@Entity` sur la classe
3. Supprimez l'import `jakarta.persistence.Entity`
4. Relancez :
   ```bash
   mvn clean verify -DskipTests
   ```
5. **Le build passe !** L'audit est satisfait.

### Etape 5 : Generer l'infrastructure JPA

1. Dans `pom.xml`, decommentez la dependance `hexaglue-plugin-jpa`
2. Lancez :
   ```bash
   mvn clean verify
   ```
3. HexaGlue genere automatiquement dans `target/hexaglue/generated-sources/` :
   - `TaskEntity.java` : Entite JPA
   - `TaskJpaRepository.java` : Repository Spring Data
   - `TaskMapper.java` : Mapper domaine/entite
   - `TaskRepositoryAdapter.java` : Adapter implementant le port driven

   Les sources generees sont ajoutees automatiquement au classpath grace a `<extensions>true</extensions>`.
4. Les tests d'integration passent et l'audit valide l'architecture finale.

---

## Resume

| Etape | Action | Resultat |
|-------|--------|----------|
| 1 | Clone | Projet pret |
| 2 | Living Doc | Documentation generee |
| 3 | Audit | Violation detectee |
| 4 | Correction | Build vert |
| 5 | JPA + Tests | Infrastructure generee, tests OK |

---

## Ressources

- [Guide de demarrage rapide](https://hexaglue.io/docs/getting-started/)
- [GitHub HexaGlue](https://github.com/hexaglue/hexaglue)
