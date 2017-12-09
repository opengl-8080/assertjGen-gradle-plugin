package com.github.opengl8080.gradle.plugin.assertj.helper;

import com.github.opengl8080.gradle.plugin.assertj.AssertjGenConfiguration;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectHelper {
    private final Project project;

    public ProjectHelper(Project project) {
        this.project = Objects.requireNonNull(project);
    }

    public void createExtension(String name, Class<?> clazz) {
        this.project.getExtensions().create(name, clazz);
    }

    public void applyPlugin(String name) {
        Map<String, String> map = new HashMap<>();
        map.put("plugin", name);
        this.project.apply(map);
    }

    public TaskHelper createTask(String name) {
        Task task = this.project.task(name);
        return new TaskHelper(task);
    }

    public Tasks getTasks(String name) {
        Set<Task> tasksByName = this.project.getTasksByName(name, false);
        return new Tasks(tasksByName);
    }
//    
//    public Tasks getCompileJavaTasks(List<String> sourceSetNames) {
//        Set<Task> tasks = sourceSetNames.stream()
//                .map(this::getSourceSet)
//                .map(SourceSetHelper::getCompileJavaTaskName)
//                .map(taskName -> this.project.getTasksByName(taskName, false))
//                .flatMap(Set::stream)
//                .collect(Collectors.toSet());
//        
//        return new Tasks(tasks);
//    }
    
    public List<String> getSourceSetOutputDirs(String sourceSetName) {
        SourceSetHelper sourceSet = this.getSourceSet(sourceSetName);
        return sourceSet.getOutputDirs();
    }

    public List<String> getSourceSetOutputDirs(List<String> sourceSetNames) {
        return sourceSetNames
                    .stream()
                    .map(this::getSourceSetOutputDirs)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
    }
    
    public List<String> getConfigurationFiles(String configurationName) {
        ConfigurationHelper configuration = this.getConfiguration(configurationName);
        return configuration.getFiles();
    }

    public List<String> getConfigurationFiles(List<String> configurationNames) {
        return configurationNames
                    .stream()
                    .map(this::getConfigurationFiles)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
    }

    public void addSourceRootIfAbsent(String sourceSetName, String path) {
        if (path == null || path.trim().isEmpty()) {
            return;
        }

        SourceSetHelper sourceSet = this.getSourceSet(sourceSetName);
        sourceSet.addSourceDirIfAbsent(path);
    }

    public AssertjGenConfiguration getAssertjGenConfiguration() {
        return (AssertjGenConfiguration) this.project.getExtensions().getByName("assertjGen");
    }

    private SourceSetHelper getSourceSet(String name) {
        SourceSetContainer sourceSets = (SourceSetContainer) this.project.getProperties().get("sourceSets");
        SourceSet sourceSet = sourceSets.getByName(name);
        return new SourceSetHelper(sourceSet);
    }

    private ConfigurationHelper getConfiguration(String name) {
        Configuration configuration = this.project.getConfigurations().getByName(name);
        return new ConfigurationHelper(configuration);
    }
    
    public Project project() {
        return this.project;
    }
}
