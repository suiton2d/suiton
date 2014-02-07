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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class FullBufferedReader extends BufferedReader {

    public FullBufferedReader(Reader in) {
        super(in);
    }

    public int readIntLine() throws IOException {
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException ex) {
            throw new IOException();
        }
    }

    public float readFloatLine() throws IOException {
        try {
            return Float.parseFloat(readLine());
        } catch (NumberFormatException ex) {
            throw new IOException();
        }
    }

    public boolean readBooleanLine() throws IOException {
        return readIntLine() == 1;
    }
}
