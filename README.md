# assertjGen-gradle-plugin
Gradle plugin that generate AssertJ assertion class.

## Example (v2.x.x)
**build.gradle**

```groovy
buildscript {
    repositories {
        maven {
          url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath "gradle.plugin.com.github.opengl-8080:assertjGen-gradle-plugin:2.0.0"
    }
}

apply plugin: "com.github.opengl-BOBO.assertjGen2"

repositories {
    mavenCentral()
}

dependencies {
    testCompile 'junit:junit:4.12'
    testCompile 'org.assertj:assertj-core:3.5.2'
}

assertjGen {
    packages = ['foo.bar']
    generateAssertionsForAllFields = true
    includes = [/foo\.bar\.Fizz.*/]
    generateJUnitSoftAssertions = false
    writeReportInFile = "${buildDir}/report.txt"
}
```

Please read [wiki](https://github.com/opengl-8080/assertjGen-gradle-plugin/wiki/Version2-in-English) for more details.
