package com.github.opengl8080.gradle.plugin.assertj;

import com.github.opengl8080.gradle.plugin.assertj.helper.ProjectHelper;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MavenProjectWrapper extends MavenProject {
    private final ProjectHelper project;

    MavenProjectWrapper(ProjectHelper project) {
        this.project = Objects.requireNonNull(project);
    }

    @Override
    public List<?> getCompileClasspathElements() throws DependencyResolutionRequiredException {
        AssertjGenConfiguration config = this.project.getAssertjGenConfiguration();

        List<Object> classpathElements = new ArrayList<>();
        
        classpathElements.addAll(this.project.getSourceSetOutputDirs("main"));
        classpathElements.addAll(this.project.getSourceSetOutputDirs(config.sourceSets));
        classpathElements.addAll(this.project.getConfigurationFiles("compile"));
        classpathElements.addAll(this.project.getConfigurationFiles(config.configurations));
        
        return classpathElements;
    }

    @Override
    public List<?> getTestClasspathElements() throws DependencyResolutionRequiredException {
        List<Object> classpathElements = new ArrayList<>();

        classpathElements.addAll(this.project.getSourceSetOutputDirs("test"));
        classpathElements.addAll(this.project.getConfigurationFiles("testCompile"));
        
        return classpathElements;
    }

    @Override
    public void addCompileSourceRoot(String path) {
        this.project.addSourceRootIfAbsent("main", path);
    }

    @Override
    public void addTestCompileSourceRoot(String path) {
        this.project.addSourceRootIfAbsent("test", path);
    }
}
