package com.github.opengl8080.gradle.plugin.assertj;

import java.util.function.Supplier;

public class DebugLogger {
    private static boolean debug = false;

    static void init(AssertjGenConfiguration config) {
        DebugLogger.debug = config.debug;
    }

    public static void debug(Supplier<String> messageSupplier) {
        if (!debug) {
            return;
        }

        String message = messageSupplier.get();
        System.out.println("[DEBUG] " + message);
    }
}
