package com.nebula2d.editor.util;

import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * JarHandler implementation that manipulates the jar file programmatically.
 *
 * Created by bonazza on 8/7/14.
 */
public class ProgrammaticJarHandler implements JarHandler {

    private JarOutputStream jos;
    private boolean isOpen;
    private FileHandle jarFile;

    public ProgrammaticJarHandler(FileHandle jarFile) {
        this.jarFile = jarFile;
    }

    public void openJar() throws IOException {
        FileInputStream fist = new FileInputStream(jarFile.file());
        File tempJarFile = new File(jarFile.name() + ".tmp");
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(tempJarFile));
        JarFile jar = new JarFile(jarFile.file());
        try {
            Enumeration entries = jar.entries();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                InputStream is = jar.getInputStream(entry);
                jos.putNextEntry(entry);
                while ((bytesRead = is.read(buffer)) != -1)
                    jos.write(buffer, 0, bytesRead);
            }
            isOpen = true;
        } catch (IOException e) {
            // Add a stub entry here, so that the jar will close without an exception.
            jos.putNextEntry(new JarEntry("stub"));
            jos.close();
            tempJarFile.delete();
            throw e;
        }
    }

    public void addToJar(String filePath) throws IOException {
        JarEntry entry = new JarEntry(filePath);
        FileInputStream fis = new FileInputStream(filePath);
        jos.putNextEntry(entry);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1)
            jos.write(buffer, 0, bytesRead);
    }

    public void closeJar() throws IOException {
        jos.close();
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
