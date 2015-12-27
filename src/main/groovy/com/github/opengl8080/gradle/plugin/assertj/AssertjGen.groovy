package com.github.opengl8080.gradle.plugin.assertj

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.JavaExec;

class AssertjGen implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('assertjGen', AssertjGenConfiguration)
        
        this.defineJavaPlugin(project)
        this.defineAssertjCleanTask(project)
        this.defineAssertjGenTask(project)
    }
    
    private void defineJavaPlugin(Project project) {
        project.apply(plugin: 'java')
        project.repositories {
            mavenCentral()
        }
    }
    
    private void defineAssertjGenTask(Project project) {
        
        project.task(type: JavaExec, dependsOn: 'assertjClean', 'assertjGen') {
            AssertjGenConfiguration conf = project.assertjGen
            File outputDir = conf.getOutputDirAsFile()
            
            doFirst {
                if (!outputDir.exists()) {
                    outputDir.mkdirs()
                }
                
                project.sourceSets.test.java.srcDirs.add(conf.getOutputDirAsFile().path)
                this.defineConfiguration(project, conf)
                
                main 'org.assertj.assertions.generator.cli.AssertionGeneratorLauncher'
                classpath = project.files(project.configurations[conf.configurationName])
                workingDir = outputDir
                args = conf.classOrPackageNames
            }
        }
        
        project.compileTestJava.dependsOn('assertjGen')
    }
    
    private void defineConfiguration(Project project, AssertjGenConfiguration conf) {
        String configurationName = conf.configurationName
        
        project.configurations.create(configurationName)
        
        project.dependencies.add(configurationName, conf.assertjGenerator)
        project.dependencies.add(configurationName, project.project(conf.projectPath))
    }
    
    private void defineAssertjCleanTask(Project project) {
        project.task(type: Delete, 'assertjClean') << {
            delete project.assertjGen.getOutputDirAsFile()
        }
    }
}
