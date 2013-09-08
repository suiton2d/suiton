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
