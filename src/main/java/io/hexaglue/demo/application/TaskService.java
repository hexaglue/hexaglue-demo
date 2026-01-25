package io.hexaglue.demo.application;

import io.hexaglue.demo.domain.Task;
import io.hexaglue.demo.domain.TaskId;
import io.hexaglue.demo.ports.in.TaskUseCases;
import io.hexaglue.demo.ports.out.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service applicatif implémentant les cas d'utilisation.
 * Orchestre les opérations métier et utilise le repository pour la persistence.
 */
public class TaskService implements TaskUseCases {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(String title, String description) {
        Task task = new Task(TaskId.generate(), title, description);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTask(TaskId id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void startTask(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tâche non trouvée: " + id));
        task.start();
        taskRepository.save(task);
    }

    @Override
    public void completeTask(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tâche non trouvée: " + id));
        task.complete();
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tâche non trouvée: " + id));
        taskRepository.delete(task);
    }
}
