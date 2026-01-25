package io.hexaglue.demo.domain;

import java.time.Instant;

// ⚠️ VIOLATION ARCHITECTURALE : import d'infrastructure dans le domaine
// Cette dépendance vers JPA viole la pureté du domaine hexagonal.
// L'audit HexaGlue détectera cette violation.
import jakarta.persistence.Entity;

/**
 * Agrégat racine représentant une tâche.
 */
public class Task {

    private final TaskId id;
    private String title;
    private String description;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Task(TaskId id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    // Pour la reconstruction depuis la persistence
    public Task(TaskId id, String title, String description, TaskStatus status,
                Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TaskId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Met à jour le titre de la tâche.
     */
    public void updateTitle(String newTitle) {
        this.title = newTitle;
        this.updatedAt = Instant.now();
    }

    /**
     * Met à jour la description de la tâche.
     */
    public void updateDescription(String newDescription) {
        this.description = newDescription;
        this.updatedAt = Instant.now();
    }

    /**
     * Démarre la tâche.
     */
    public void start() {
        if (this.status != TaskStatus.TODO) {
            throw new IllegalStateException("Seule une tâche TODO peut être démarrée");
        }
        this.status = TaskStatus.IN_PROGRESS;
        this.updatedAt = Instant.now();
    }

    /**
     * Termine la tâche.
     */
    public void complete() {
        if (this.status == TaskStatus.DONE) {
            throw new IllegalStateException("La tâche est déjà terminée");
        }
        this.status = TaskStatus.DONE;
        this.updatedAt = Instant.now();
    }

    /**
     * Réouvre la tâche.
     */
    public void reopen() {
        this.status = TaskStatus.TODO;
        this.updatedAt = Instant.now();
    }
}
