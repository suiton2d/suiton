/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.util;

import java.awt.*;
import java.io.File;
import java.util.Locale;


/**
 * Utility class for platform specific functionality
 */
public class PlatformUtil {

    /**
     * Finds the index of the platform specific file separator character in the provided string.
     *
     * @param base The string for which to find the character.
     * @return The index of the platform specific file separator. If no instance of the separator exists in the string,
     * -1 is returned.
     */
    public static int findLastSlashChar(String base) {
        return base.lastIndexOf(File.separator);
    }

    /**
     * Determines whether or not the app is running on a Mac computer.
     *
     * @return Whether or not the app is running on a mac computer.
     */
    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    /**
     * Joins a number of paths into one path.
     * @param paths an array of paths.
     *
     * @return the conjoined path.
     */
    public static String pathJoin(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (!path.endsWith(File.pathSeparator))
                path += File.pathSeparator;
            sb.append(path);
        }

        return sb.toString();
    }

    public static String getCommandChainString() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (os.contains("win")) {
            return "&";
        } else {
            return "&&";
        }
    }

    public static Dimension getScreenSize() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getScreenSize();
    }

    public static int getScreenWidth() {
        return getScreenSize().width;
    }

    public static int getScreenHeight() {
        return getScreenSize().height;
    }
}
