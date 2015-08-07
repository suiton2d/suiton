package com.suiton2d.editor.util;

import java.io.File;
import java.io.IOException;

public class GradleExecutor {
    public static Process process;
    public static void build(String dir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("gradle", "clean", "fatJar");
        process = pb.directory(new File(dir)).start();
        process.waitFor();
    }
}
