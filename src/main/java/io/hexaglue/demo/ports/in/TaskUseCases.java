package io.hexaglue.demo.ports.in;

import io.hexaglue.demo.domain.Task;
import io.hexaglue.demo.domain.TaskId;

import java.util.List;
import java.util.Optional;

/**
 * Port entrant définissant les cas d'utilisation de gestion des tâches.
 * Ce port driving est implémenté par le service applicatif.
 */
public interface TaskUseCases {

    /**
     * Crée une nouvelle tâche.
     */
    Task createTask(String title, String description);

    /**
     * Récupère une tâche par son identifiant.
     */
    Optional<Task> getTask(TaskId id);

    /**
     * Liste toutes les tâches.
     */
    List<Task> listAllTasks();

    /**
     * Démarre une tâche.
     */
    void startTask(TaskId id);

    /**
     * Termine une tâche.
     */
    void completeTask(TaskId id);

    /**
     * Supprime une tâche.
     */
    void deleteTask(TaskId id);
}
