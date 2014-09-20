package com.nebula2d.editor.util;

import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public void addToJar(String filePath, String dir) throws IOException {

        try {
            Process p = new ProcessBuilder("jar", "uf", jarFile.path(), "-C", dir, filePath).start();
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

    private List<String> listFiles(String prefix) throws Exception {
        BufferedReader br = null;
        try {
            Process p = new ProcessBuilder("jar", "tf", jarFile.path()).start();
            int rc = p.waitFor();
            if (rc != 0)
                throw new Exception();

            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            List<String> results = new ArrayList<>();
            String tmp;

            while ((tmp = br.readLine()) != null) {
                if (tmp.startsWith(prefix))
                    results.add(tmp);
            }

            return results;
        } finally {
            if (br != null)
                br.close();
        }
    }

    @Override
    public void extract(String file, String out) throws IOException {
        try {
            List<String> files = listFiles(file);
            for (String s : files) {
                File f = new File(s);
                if (!f.isDirectory()) {
                    Process p = new ProcessBuilder("cd", out,
                            PlatformUtil.getCommandChainString(), "jar", "xf", s).start();
                    int rc = p.waitFor();

                    if (rc != 0)
                        throw new Exception();
                }
            }
        } catch (Exception e) {
            throw new IOException("Failed to extract file");
        }
    }
}
