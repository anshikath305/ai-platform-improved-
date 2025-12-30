package com.ai.platform.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ErrorLogger {

    private static final String LOG_PATH = "/tmp/ai-platform-errors.log";

    public static void log(Exception e) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_PATH, true))) {

            writer.println("\n==== ERROR @ "+ LocalDateTime.now() +" ====");
            e.printStackTrace(writer);
            writer.println("======================================");

        } catch (Exception ignore) {}
    }
}
