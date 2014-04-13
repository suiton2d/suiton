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

import java.io.File;


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
    public static int findSlashChar(String base) {
        int slash = base.indexOf(File.separator);

        return slash;
    }

    /**
     * Determines whether or not the app is running on a Mac computer.
     *
     * @return Whether or not the app is running on a mac computer.
     */
    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
}