package io.hexaglue.demo.ports.out;

import io.hexaglue.demo.domain.Task;
import io.hexaglue.demo.domain.TaskId;

import java.util.List;
import java.util.Optional;

/**
 * Port sortant définissant les opérations de persistence des tâches.
 * Ce port driven sera implémenté par un adapter JPA généré par HexaGlue.
 */
public interface TaskRepository {

    /**
     * Sauvegarde une tâche.
     */
    Task save(Task task);

    /**
     * Recherche une tâche par son identifiant.
     */
    Optional<Task> findById(TaskId id);

    /**
     * Liste toutes les tâches.
     */
    List<Task> findAll();

    /**
     * Supprime une tâche.
     */
    void delete(Task task);

    /**
     * Vérifie si une tâche existe.
     */
    boolean existsById(TaskId id);
}
