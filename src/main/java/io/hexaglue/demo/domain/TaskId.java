package io.hexaglue.demo.domain;

import java.util.UUID;

/**
 * Identifiant unique d'une tâche.
 * Value Object immutable.
 */
public record TaskId(UUID value) {

    public static TaskId generate() {
        return new TaskId(UUID.randomUUID());
    }

    public static TaskId fromString(String id) {
        return new TaskId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
