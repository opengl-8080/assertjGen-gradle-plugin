package com.github.opengl8080.gradle.plugin.assertj;

import com.github.opengl8080.gradle.plugin.assertj.helper.ProjectHelper;
import com.github.opengl8080.gradle.plugin.assertj.helper.TaskHelper;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.assertj.maven.AssertJAssertionsGeneratorMojo;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertjGen implements Plugin<Project> {
    private static final Logger logger = LoggerFactory.getLogger(AssertjGen.class);

    @Override
    public void apply(Project originalProject) {
        ProjectHelper project = new ProjectHelper(originalProject);
        
        project.createExtension("assertjGen", AssertjGenConfiguration.class);
        project.applyPlugin("java");
        
        this.defineAssertjGenTask(project);
    }
    
    private void defineAssertjGenTask(ProjectHelper project) {
        TaskHelper assertjGen = project.createTask("assertjGen");

        assertjGen.doLast(() -> {
            AssertJAssertionsGeneratorMojo mojo = new AssertJAssertionsGeneratorMojo();
            mojo.project = new MavenProjectWrapper(project);

            AssertjGenConfiguration config = project.getAssertjGenConfiguration();
            DebugLogger.init(config);
            
            DebugLogger.debug(config::toString);

            mojo.targetDir = config.resolveTargetDir(project.project());
            mojo.generateAssertionsInPackage = config.generateAssertionsInPackage;
            mojo.cleanTargetDir = config.cleanTargetDir;
            mojo.generatedSourcesScope = config.generatedSourcesScope;
            mojo.packages = config.packages;
            mojo.classes = config.classes;
            mojo.includes = config.includes;
            mojo.excludes = config.excludes;
            mojo.hierarchical = config.hierarchical;
            mojo.generateAssertionsForAllFields = config.generateAssertionsForAllFields;
            mojo.entryPointClassPackage = config.entryPointClassPackage;
            mojo.skip = config.skip;
            mojo.generateAssertions = config.generateAssertions;
            mojo.generateBddAssertions = config.generateBddAssertions;
            mojo.generateJUnitSoftAssertions = config.generateJUnitSoftAssertions;
            mojo.generateSoftAssertions = config.generateSoftAssertions;
            mojo.quiet = config.quiet;
            mojo.writeReportInFile = config.writeReportInFile;
            mojo.templates = config.templates;

            try {
                mojo.execute();
            } catch (MojoFailureException | MojoExecutionException e) {
                throw new RuntimeException("failed to execute AssertJAssertionsGeneratorMojo", e);
            }
        });
        

        // compileJava <- assertjGen
        assertjGen.dependsOn(project.getTasks("compileJava"));
        // assertjGen <- compileTestJava
        project.getTasks("compileTestJava").dependsOn(assertjGen.task());
    }
}
