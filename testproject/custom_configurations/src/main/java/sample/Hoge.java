package sample;

import com.github.javaparser.JavaParser;
import org.antlr.v4.runtime.TokenSource;

public class Hoge {
    private int testValue;

    public int getTestValue() {
        return testValue;
    }

    public void setTestValue(int testValue) {
        this.testValue = testValue;
    }

    public static void foo(TokenSource tokenSource) {
        System.out.println(tokenSource);
    }
    
    public static void fuga(JavaParser parser) throws Exception {
        JavaParser.parse("");
    }
}