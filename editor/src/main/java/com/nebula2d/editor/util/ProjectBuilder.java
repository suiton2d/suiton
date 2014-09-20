package com.nebula2d.editor.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Layer;
import com.nebula2d.editor.framework.Project;
import com.nebula2d.editor.framework.Scene;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.FileHandler;

/**
 * ProjectBuilder is a helper class for building a Nebula2D Project.
 *
 * Created by bonazza on 8/7/14.
 */
public class ProjectBuilder {

    public static enum ProjectType {
        PC("desktop.jar"),
        MAC("desktop.jar"),
        LINUX("desktop.jar"),
        ANDROID("android.jar");

        private String jarName;

        private ProjectType(String jarName) {
            this.jarName = jarName;
        }

        public String getJarName() {
            return jarName;
        }
    }
    private Project project;

    public ProjectBuilder(Project project) {
        this.project = project;
    }

    public void build(int startScene, ProjectType type) throws IOException {
        FileHandle jarFile = extractJar(type);
        JarHandler jarHandler = new ExternalJarHandler(jarFile);
        String projectTmpDir = project.getTempDir();
        FileHandle sceneFileHandle = Gdx.files.absolute(PlatformUtil.pathJoin(projectTmpDir, "scenes.xml"));
        FileHandle assetsFileHandle = Gdx.files.absolute(PlatformUtil.pathJoin(projectTmpDir, "assets.xml"));
        project.build(startScene, sceneFileHandle, assetsFileHandle);
        jarHandler.openJar();
        jarHandler.addToJar("scenes.xml", projectTmpDir);
        jarHandler.addToJar("assets.xml", projectTmpDir);
        jarHandler.closeJar();
    }

    public FileHandle extractJar(ProjectType type) throws IOException {
        String jarName = type.getJarName();
        FileHandle dest = Gdx.files.absolute(PlatformUtil.pathJoin(project.getTempDir(), jarName));

        FileHandle jarHandle = Gdx.files.classpath(PlatformUtil.pathJoin("clients", jarName));
        jarHandle.copyTo(dest);

        return dest;
    }
}
