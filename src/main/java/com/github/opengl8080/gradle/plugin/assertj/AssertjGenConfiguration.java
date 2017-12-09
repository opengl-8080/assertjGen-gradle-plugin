package com.github.opengl8080.gradle.plugin.assertj;

import groovy.lang.Closure;
import org.assertj.maven.Templates;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssertjGenConfiguration {
    public String targetDir;
    public String generateAssertionsInPackage;
    public boolean cleanTargetDir = false;
    public String generatedSourcesScope = "test";
    public String[] packages = {};
    public String[] classes = {};
    public String[] includes = {};
    public String[] excludes = {};
    public boolean hierarchical = true;
    public boolean generateAssertionsForAllFields = false;
    public String entryPointClassPackage;
    public boolean skip = false;
    public boolean generateAssertions = true;
    public boolean generateBddAssertions = true;
    public boolean generateJUnitSoftAssertions = true;
    public boolean generateSoftAssertions = true;
    public boolean quiet = false;
    public String writeReportInFile;
    Templates templates;

    public void setTemplates(Closure<?> closure) {
        Templates templates = new Templates();
        closure.setDelegate(templates);
        closure.call();
        this.templates = templates;
    }
    
    public List<String> configurations = new ArrayList<>();
    public List<String> sourceSets = new ArrayList<>();
    
    public boolean debug = false;

    public String resolveTargetDir(Project project) {
        String targetDir = this.targetDir != null
                ? this.targetDir
                : project.getBuildDir().getPath() + "/generated-test-sources/assertj-assertions";

        DebugLogger.debug(() -> "target = " + targetDir);
        String resolvedTargetDir = project.file(targetDir).getPath();
        DebugLogger.debug(() -> "resolvedTargetDir = " + resolvedTargetDir);

        return resolvedTargetDir;
    }

    @Override
    public String toString() {
        return "AssertjGenConfiguration{" +
                "targetDir='" + targetDir + '\'' +
                ", generateAssertionsInPackage='" + generateAssertionsInPackage + '\'' +
                ", cleanTargetDir=" + cleanTargetDir +
                ", generatedSourcesScope='" + generatedSourcesScope + '\'' +
                ", packages=" + Arrays.toString(packages) +
                ", classes=" + Arrays.toString(classes) +
                ", includes=" + Arrays.toString(includes) +
                ", excludes=" + Arrays.toString(excludes) +
                ", hierarchical=" + hierarchical +
                ", generateAssertionsForAllFields=" + generateAssertionsForAllFields +
                ", entryPointClassPackage='" + entryPointClassPackage + '\'' +
                ", skip=" + skip +
                ", generateAssertions=" + generateAssertions +
                ", generateBddAssertions=" + generateBddAssertions +
                ", generateJUnitSoftAssertions=" + generateJUnitSoftAssertions +
                ", generateSoftAssertions=" + generateSoftAssertions +
                ", quiet=" + quiet +
                ", writeReportInFile='" + writeReportInFile + '\'' +
                ", templates=" + templates +
                ", configurations=" + configurations +
                ", sourceSets=" + sourceSets +
                ", debug=" + debug +
                '}';
    }
}
