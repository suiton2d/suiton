package com.nebula2d.editor.util;

import java.io.File;
import java.io.IOException;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public class GradleExecutor {

    public static void build(String dir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("gradle", "clean", "fatJar");
        int rc = pb.directory(new File(dir)).start().waitFor();
        System.out.println(rc);
    }
}
