package com.nebula2d.editor.util;

import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * JarHandler implementation that uses the external "jar" command line application provided with the jdk.
 *
 * Created by bonazza on 8/7/14.
 */
public class ExternalJarHandler implements JarHandler {

    private FileHandle jarFile;
    private boolean isOpen;

    public ExternalJarHandler(FileHandle jarFile) {
        this.jarFile = jarFile;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void openJar() throws IOException {
        isOpen = true;
    }

    @Override
    public void addToJar(String filePath) throws IOException {

        try {
            Process p = new ProcessBuilder("jar", "uf", jarFile.path(), filePath).start();
            int rc = p.waitFor();

            if (rc != 0)
                throw new Exception();
        } catch (Exception e) {
            throw new IOException("Failed to update jar file.");
        }
    }

    @Override
    public void closeJar() throws IOException {
        isOpen = false;
    }
}
