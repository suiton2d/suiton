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
     */
    public void addToJar(String filePath) throws IOException;

    /**
     * Closes the jar file.
     * @throws IOException
     */
    public void closeJar() throws IOException;
}
