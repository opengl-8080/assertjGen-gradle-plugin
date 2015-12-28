# assertjGen-gradle-plugin
Gradle plugin that generate AssertJ assertion class.

# Installation
See [Gradle - Plugin: com.github.opengl-BOBO.assertjGen](https://plugins.gradle.org/plugin/com.github.opengl-BOBO.assertjGen)

# What is this?
This plugin add `assertjGen` task.  

This task generates **Assertion Classes** by [AssertJ Assertions Generator](http://joel-costigliola.github.io/assertj/assertj-assertions-generator.html).

# Configuration

**build.gradle**

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.opengl-8080:assertjGen-gradle-plugin:1.0.0"
  }
}

apply plugin: "com.github.opengl-BOBO.assertjGen"

assertjGen {
    // specify target class or package names by array. (defailt is empty array)
    classOrPackageNames = ['foo.bar']
    
    // specify output dir(String path or File object). (default is 'src/test/java-gen')
    outputDir = 'src/test/foo-bar'
    
    // specify AssertJ Assertions Generator dependency. (default is ver 2.0.0)
    assertjGenerator = 'org.assertj:assertj-assertions-generator:2.0.0'
}
```

At least you must set `classOrPackageNames` option.

# Defined Tasks
## assertjGen
This task generates assertion classes.

### dependsOn
`compileTestJava` -> `assertjGen` -> `assertjClean` and `compileJava`

So you run `test` task, then `assertjGen` task is also run.

## assertjClean
This task deletes generated classes (`*.java` files).

If you run `clean` task, then `assertjClean` task is also run.

