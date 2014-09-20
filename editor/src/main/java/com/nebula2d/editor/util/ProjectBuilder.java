package com.nebula2d.editor.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.nebula2d.editor.framework.Project;

import java.io.*;

/**
 * ProjectBuilder is a helper class for building a Nebula2D Project.
 *
 * Created by bonazza on 8/7/14.
 */
public class ProjectBuilder {

    public static enum ProjectType {
        PC("desktop"),
        MAC("desktop"),
        LINUX("desktop"),
        ANDROID("android");

        private String sourcePath;

        private ProjectType(String sourcePath) {
            this.sourcePath = sourcePath;
        }

        public String getSourcePath() {
            return sourcePath;
        }
    }
    private Project project;

    public ProjectBuilder(Project project) {
        this.project = project;
    }

    public void build(int startScene, ProjectType type) throws IOException {
        FileHandle source = extractSource(type);
        String projectTmpDir = project.getTempDir();
        FileHandle sceneFileHandle = Gdx.files.absolute(PlatformUtil.pathJoin(projectTmpDir, "scenes.xml"));
        FileHandle assetsFileHandle = Gdx.files.absolute(PlatformUtil.pathJoin(projectTmpDir, "assets.xml"));
        project.build(startScene, sceneFileHandle, assetsFileHandle);
        FileHandle dest = Gdx.files.absolute(PlatformUtil.pathJoin(source.path(), "resources"));
        source.copyTo(dest);
        GradleExecutor.build(source.path());
    }

    public FileHandle extractSource(ProjectType type) throws IOException {
        String sourcePath = type.getSourcePath();
        FileHandle dest = Gdx.files.absolute(PlatformUtil.pathJoin(project.getTempDir(), sourcePath));

        FileHandle sourceHandle = Gdx.files.classpath(PlatformUtil.pathJoin("clients", sourcePath));
        sourceHandle.copyTo(dest);

        return dest;
    }
}
