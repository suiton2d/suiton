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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class FullBufferedWriter extends BufferedWriter {

    public FullBufferedWriter(Writer out) {
        super(out);
    }

    public void writeLine(String str) throws IOException {
        write(str + "\n");
    }
    public void writeBoolLine(boolean bool) throws IOException {
        int tmp = bool ? 1 : 0;
        writeIntLine(tmp);
    }

    public void writeIntLine(int i) throws IOException {
        try {
            writeLine(Integer.toString(i));
        } catch (NumberFormatException e) {
            throw new IOException();
        }
    }

    public void writeFloatLine(float f) throws IOException {
        try {
            writeLine(Float.toString(f));
        } catch (NumberFormatException ex) {
            throw new IOException();
        }
    }
}
