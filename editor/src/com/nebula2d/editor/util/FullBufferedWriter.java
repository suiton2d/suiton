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
        write(i);
        write('\n');
    }

    public void writeFloatLine(float f) throws IOException {
        String fstr;

        try {
            fstr = Float.toString(f);
        } catch (NumberFormatException ex) {
            throw new IOException();
        }

        writeLine(fstr);
    }
}
