package com.nebula2d.editor.util;

import java.io.File;


/**
 * Utility class for platform specific functionality
 */
public class PlatformUtil {

    public static int findSlashChar(String base) {
        int slash = base.indexOf(File.separator);

        return slash;
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
}
