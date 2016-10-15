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

### cleanOnlyFiles option
If you want output files into a directory that includes handmade files (e.g. `src/test/java`), please set `cleanOnlyFiles` option to `true`.

```groovy
assertjGen {
    classOrPackageNames = ['foo.bar']
    outputDir = 'src/test/java'
    cleanOnlyFiles = true
}
```

If `cleanOnlyFiles` option is `false` (default is `false`), `assertjClean` task deletes all files under an `outputDir`.  
So handmade files will be deleted.

If `cleanOnlyFiles` option is `true`, `assertjClean` task only deletes matched files by `cleanFilesPattern` option.  
`cleanFilesPattern` option is regular expression. Its default value is `/^.*Assert\.java$/`.


# Defined Tasks
## assertjGen
This task generates assertion classes.

### dependsOn
`compileTestJava` -> `assertjGen` -> `assertjClean` and `compileJava`

So you run `test` task, then `assertjGen` task is also run.

## assertjClean
This task deletes generated classes (`*.java` files).

If you run `clean` task, then `assertjClean` task is also run.

# 日本語の説明
## インストール方法
[Gradle - Plugin: com.github.opengl-BOBO.assertjGen](https://plugins.gradle.org/plugin/com.github.opengl-BOBO.assertjGen)

このページに設定方法が載っているので、そちらを参照ください。

## これはなに？
`assertjGen` タスクを追加するプラグインです。

このタスクは、 [AssertJ Assertions Generator](http://joel-costigliola.github.io/assertj/assertj-assertions-generator.html) を使用してアサーションクラスを生成します。

アサーションクラスが何なのかについては、リンク先を参照していただくか、 [こちらの Qiita の記事](http://qiita.com/opengl-8080/items/b07307ab0d33422be9c5#%E6%A4%9C%E8%A8%BC%E7%94%A8%E3%82%AF%E3%83%A9%E3%82%B9%E3%82%92%E8%87%AA%E5%8B%95%E7%94%9F%E6%88%90%E3%81%99%E3%82%8B) を参照してください。

## 設定

**build.gradle**

```groovy
assertjGen {
    // 対象のクラスか、パッケージ名を String のリストで指定します。（デフォルトは空のリストです）
    classOrPackageNames = ['foo.bar']
    
    // 生成されたファイルの出力先を指定します（String のパスで指定するか、 File オブジェクトで指定します）（デフォルトは src/test/java-gen です）
    outputDir = 'src/test/foo-bar'
    
    // AssertJ Assertions Generator の依存関係を指定します（デフォルトは 2.0.0 を使用します）
    assertjGenerator = 'org.assertj:assertj-assertions-generator:2.0.0'
}
```

最低でも `classOrPackageNames` オプションを指定すれば OK です。

### cleanOnlyFiles オプション
もし自動生成したファイルを手で作成したファイルを含むディレクトリ（例：`src/test/java`）に出力したい場合は、 `cleanOnlyFiles` オプションに `true` を指定してください。

```groovy
assertjGen {
    classOrPackageNames = ['foo.bar']
    outputDir = 'src/test/java'
    cleanOnlyFiles = true
}
```

もし `cleanOnlyFiles` オプションが `false` （デフォルトは `false`）だと、 `assertjClean` タスクは `outputDir` 以下の全てのファイルを削除します。  
そのため、手で作ったファイルも削除されてしまいます。

`cleanOnlyFiles` オプションに `true` を設定すれば、 `assertjClean` タスクは `cleanFilesPattern` オプションにマッチするファイルだけを削除します。  
`cleanFilesPattern` オプションは正規表現で、デフォルト値は `/^.*Assert\.java$/` です。


## 追加されるタスク
### assertjGen
アサーションクラスを生成するタスクです。

#### タスクの依存関係
`compileTestJava` -> `assertjGen` -> `assertjClean` and `compileJava`

なので、 `test` タスクを実行すれば `assertjGen` も一緒に実行されます。

### assertjClean
生成したアサーションクラス（`*.java` ファイル）を削除します。

このタスクは、 `clean` タスクを実行すると一緒に実行されます。

# Release Note
## English
- v1.1.0 (2016-10-15)
    - Add `cleanOnlyFiles` and `cleanFilesPattern` options.
- v1.0.0
    - First Release

## 日本語
- v1.1.0 (2016-10-15)
    - `cleanOnlyFiles` と `cleanFilesPattern` オプションを追加。
- v1.0.0
    - 初回リリース
