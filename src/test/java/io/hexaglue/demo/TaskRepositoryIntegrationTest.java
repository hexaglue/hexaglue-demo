package io.hexaglue.demo;

import io.hexaglue.demo.domain.Task;
import io.hexaglue.demo.domain.TaskId;
import io.hexaglue.demo.domain.TaskStatus;
import io.hexaglue.demo.ports.out.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests d'intégration pour le repository de tâches.
 *
 * ⚠️ Ce test ne fonctionnera qu'après :
 * 1. Avoir corrigé la violation architecturale dans Task.java
 * 2. Avoir généré l'infrastructure JPA avec hexaglue-plugin-jpa
 * 3. Avoir configuré le bean TaskRepository dans Spring
 */
@SpringBootTest
class TaskRepositoryIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Devrait sauvegarder et retrouver une tâche")
    void shouldSaveAndFindTask() {
        // Given
        Task task = Task.create(TaskId.generate(), "Ma première tâche", "Description de test");

        // When
        Task saved = taskRepository.save(task);
        Optional<Task> found = taskRepository.findById(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Ma première tâche");
        assertThat(found.get().getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    @DisplayName("Devrait lister toutes les tâches")
    void shouldListAllTasks() {
        // Given
        taskRepository.save(Task.create(TaskId.generate(), "Tâche 1", "Description 1"));
        taskRepository.save(Task.create(TaskId.generate(), "Tâche 2", "Description 2"));

        // When
        List<Task> tasks = taskRepository.findAll();

        // Then
        assertThat(tasks).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("Devrait supprimer une tâche")
    void shouldDeleteTask() {
        // Given
        Task task = Task.create(TaskId.generate(), "Tâche à supprimer", "Sera supprimée");
        Task saved = taskRepository.save(task);

        // When
        taskRepository.delete(saved);

        // Then
        assertThat(taskRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("Devrait mettre à jour le statut d'une tâche")
    void shouldUpdateTaskStatus() {
        // Given
        Task task = Task.create(TaskId.generate(), "Tâche à démarrer", "Test du workflow");
        Task saved = taskRepository.save(task);

        // When
        saved.start();
        taskRepository.save(saved);
        Optional<Task> updated = taskRepository.findById(saved.getId());

        // Then
        assertThat(updated).isPresent();
        assertThat(updated.get().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }
}
