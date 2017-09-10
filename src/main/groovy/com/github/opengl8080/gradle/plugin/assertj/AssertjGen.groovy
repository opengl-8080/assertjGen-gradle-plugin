package com.github.opengl8080.gradle.plugin.assertj

import groovy.io.FileType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.JavaExec
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AssertjGen implements Plugin<Project> {
    
    private static final Logger logger = LoggerFactory.getLogger(AssertjGen)

    private static final Map DEFAULT_PRE_CONFIG = [
            "assertjGenerator": "org.assertj:assertj-assertions-generator:2.0.0",
            "configurationName": "assertj"
    ]
    
    @Override
    void apply(Project project) {
        project.extensions.create('assertjGen', AssertjGenConfiguration)
        
        this.defineJavaPlugin(project)
        this.defineAssertjCleanTask(project)
        this.defineAssertjGenTask(project)
        this.defineConfiguration(project)
    }
    
    private void defineJavaPlugin(Project project) {
        project.apply(plugin: 'java')
    }
    
    private void defineAssertjGenTask(Project project) {
        
        project.task(type: JavaExec, dependsOn: ['assertjClean', 'compileJava'], 'assertjGen') {
            doFirst {
                File outputDir = this.resolveOutputDir(project)
                
                if (!outputDir.exists()) {
                    outputDir.mkdirs()
                }
                
                AssertjGenConfiguration conf = project.assertjGen
                
                if (conf.classOrPackageNames.isEmpty()) {
                    logger.warn('* classOrPackageNames is empty. Please to specify target class or package names.')
                }
                
                this.addSrcDir(project)
                this.debugLog(project, conf)
                
                Map preConfig = this.getPreConfig(project)
                
                main 'org.assertj.assertions.generator.cli.AssertionGeneratorLauncher'
                classpath = project.files(project.configurations[preConfig.configurationName])
                workingDir = outputDir
                args = conf.classOrPackageNames
            }
        }
        
        project.compileTestJava.dependsOn('assertjGen')
    }
    
    private void addSrcDir(Project project) {
        File outputDir = this.resolveOutputDir(project)
        project.sourceSets.test.java.srcDirs.add(outputDir.path)
    }
    
    private void defineConfiguration(Project project) {
        Map preConfig = this.getPreConfig(project)
        String configurationName = preConfig.configurationName
        
        project.configurations.create(configurationName)
        
        project.dependencies.add(configurationName, preConfig.assertjGenerator)
        project.dependencies.add(configurationName, project.files(project.compileJava.destinationDir))
    }
    
    private void defineAssertjCleanTask(Project project) {
        project.task(type: Delete, 'assertjClean')
        project.assertjClean.doLast {
            this.clean(project)
        }

        project.clean.doFirst {
            this.clean(project)
        }
    }
    
    private void clean(Project project) {
        if (!project.assertjGen.cleanOnlyFiles) {
            project.delete this.resolveOutputDir(project)
            return
        }
        
        File outputDir = this.resolveOutputDir(project)

        if (!outputDir.exists()) {
            return
        }
        
        outputDir.eachFileRecurse(FileType.FILES) { file ->
            if (file.name =~ project.assertjGen.cleanFilesPattern) {
                file.delete()
            }
        }
    }
    
    private File resolveOutputDir(Project project) {
        File outputDir = project.assertjGen.getOutputDirAsFile()
        if (outputDir.isAbsolute()) {
            return outputDir
        } else {
            return new File(project.projectDir, outputDir.path)
        }
    }
    
    private Map getPreConfig(Project project) {
        if (!project.hasProperty("assertjGenPreConfig")) {
            project.ext.set("assertjGenPreConfig", [:])
        }

        DEFAULT_PRE_CONFIG.each { key, value ->
            if (!project.assertjGenPreConfig.containsKey(key)) {
                project.assertjGenPreConfig.put(key, value)
            }
        }
        
        return project.assertjGenPreConfig
    }
    
    private void debugLog(Project project, AssertjGenConfiguration conf) {
        if (logger.isDebugEnabled()) {
            logger.debug("""\
            |configurationName = ${conf.configurationName}
            |classpath = ${project.configurations[conf.configurationName]}
            |outputDir = ${conf.getOutputDirAsFile()}
            |classOrPackageNames = ${conf.classOrPackageNames}
            |cleanOnlyFiles = ${conf.cleanOnlyFiles}
            |cleanFilesPattern = ${conf.cleanFilesPattern}
            |""".stripMargin())
        }
    }
}
