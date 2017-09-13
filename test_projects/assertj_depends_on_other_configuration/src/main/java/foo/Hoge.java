package foo;

import org.antlr.v4.runtime.TokenSource;

public class Hoge {
    private int age;

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void foo(TokenSource tokenSource) {
        System.out.println(tokenSource);
    }
}