package com.nebula2d.editor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 8/2/13
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
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
