package com.github.opengl8080.gradle.plugin.assertj.helper;

import org.gradle.api.Task;

import java.util.Objects;

public class TaskHelper {
    private final Task task;

    TaskHelper(Task task) {
        this.task = Objects.requireNonNull(task);
    }

    public void doLast(DoLast block) {
        this.task.doLast(task -> block.execute());
    }

    public void dependsOn(Tasks tasks) {
        tasks.areDependedFrom(this.task);
    }

    public Task task() {
        return this.task;
    }
    
    @FunctionalInterface
    public interface DoLast {
        void execute();
    }
}
