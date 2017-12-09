package com.github.opengl8080.gradle.plugin.assertj.helper;

import org.gradle.api.Task;

import java.util.Objects;
import java.util.Set;

public class Tasks {
    private final Set<Task> tasks;

    Tasks(Set<Task> tasks) {
        this.tasks = Objects.requireNonNull(tasks);
    }

    void areDependedFrom(Task task) {
        this.tasks.forEach(task::dependsOn);
    }

    public void dependsOn(Task dependent) {
        this.tasks.forEach(task -> task.dependsOn(dependent));
    }
}
