package com.github.opengl8080.gradle.plugin.assertj.helper;

import com.github.opengl8080.gradle.plugin.assertj.DebugLogger;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.SourceSet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SourceSetHelper {
    private final SourceSet sourceSet;

    SourceSetHelper(SourceSet sourceSet) {
        this.sourceSet = Objects.requireNonNull(sourceSet);
    }

    void addSourceDirIfAbsent(String srcDirPath) {
        if (this.has(srcDirPath)) {
            DebugLogger.debug(() -> "skip to add source dir : " + srcDirPath);
            return;
        }

        this.addSourceDir(srcDirPath);
    }
    
    private boolean has(String path) {
        File file = new File(path);
        return this.sourceSet.getJava().getSrcDirs().contains(file);
    }
    
    private void addSourceDir(String srcDirPath) {
        this.sourceSet.getJava().srcDir(srcDirPath);
        DebugLogger.debug(() -> "add source dir : " + srcDirPath);
    }

    List<String> getOutputDirs() {
        List<String> list = new ArrayList<>();
        
        FileCollection classesDirs = this.sourceSet.getOutput().getClassesDirs();
        classesDirs.forEach(classesDir -> list.add(classesDir.getPath()));
        
        return list;
    }
    
    String getCompileJavaTaskName() {
        return this.sourceSet.getCompileJavaTaskName();
    }
}
