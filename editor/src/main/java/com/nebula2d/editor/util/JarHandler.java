package com.nebula2d.editor.util;

import java.io.IOException;

/**
 * Interface for manipulating Jars
 *
 * Created by bonazza on 8/7/14.
 */
public interface JarHandler {

    /**
     * Opens the jar file.
     * @throws IOException
     */
    public void openJar() throws IOException;

    /**
     * Add a file to the jar.
     * @param filePath the file path of the file to add.
     * @param dir the path to switch to before adding the file
     */
    public void addToJar(String filePath, String dir) throws IOException;

    /**
     * Closes the jar file.
     * @throws IOException
     */
    public void closeJar() throws IOException;

    /**
     * Extracts a particular file (or directory of files) from a jar file.
     * @param file The file (or directory) to extract.
     * @param out The output directory.
     * @throws IOException
     */
    public void extract(String file, String out) throws IOException;
}
