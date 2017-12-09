package com.github.opengl8080.gradle.plugin.assertj.helper;

import org.gradle.api.artifacts.Configuration;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class ConfigurationHelper {
    private final Configuration configuration;

    ConfigurationHelper(Configuration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    List<String> getFiles() {
        return this.configuration.getFiles()
                .stream()
                .map(File::getPath)
                .collect(Collectors.toList());
    } 
}
