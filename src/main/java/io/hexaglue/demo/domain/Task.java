package io.hexaglue.demo.domain;

import jakarta.persistence.Entity;
import java.time.Instant;

/**
 * Agrégat racine représentant une tâche.
 */
@Entity
public class Task {

    private final TaskId id;
    private String title;
    private String description;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    /**
     * Crée une nouvelle tâche.
     *
     * @param id l'identifiant de la tâche
     * @param title le titre de la tâche
     * @param description la description de la tâche
     * @return une nouvelle tâche
     */
    public static Task create(TaskId id, String title, String description) {
        return new Task(id, title, description, TaskStatus.TODO, Instant.now(), Instant.now());
    }

    /**
     * Constructeur pour la reconstitution depuis la persistence.
     */
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
