package com.github.opengl8080.gradle.plugin.assertj

class AssertjGenConfiguration {
    Object outputDir = 'src/test/java-gen'

    boolean cleanOnlyFiles = false
    String cleanFilesPattern = /^.*Assert\.java$/

    List<String> classOrPackageNames = []
    
    File getOutputDirAsFile() {
        if (this.outputDir instanceof String) {
            return new File(this.outputDir)
        } else if (this.outputDir instanceof File) {
            return this.outputDir
        } else {
            throw new IllegalArgumentException('outputDir must be String or File object. : outputDir.class = ' + this.outputDir?.class)
        }
    }
}
