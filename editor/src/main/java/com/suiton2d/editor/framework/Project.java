/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.suiton2d.editor.framework;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlWriter;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.savers.SceneSaver;
import com.suiton2d.editor.ui.build.BuildProgressUpdateListener;
import com.suiton2d.editor.util.PlatformUtil;
import com.suiton2d.editor.util.builders.SceneBuilder;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Project {
    private String projectDir;
    private String projectName;
    private SceneManager sceneManager;

    @SuppressWarnings("all")
    public Project(String dir, String name, SceneManager sceneManager) {
        this.projectDir = dir;
        this.projectName = name;
        this.sceneManager = sceneManager;
        if (this.projectName.endsWith(".n2d"))
            this.projectName = this.projectName.substring(0, this.projectName.length() - 4);
        File assetsDir = new File(getAssetsDirPath());
        assetsDir.mkdir();
    }

    @SuppressWarnings("all")
    public Project(String path, SceneManager sceneManager) {
        File file = new File(path);
        this.projectDir = file.getParent();
        this.projectName = file.getName();
        if (this.projectName.endsWith(".n2d"))
            this.projectName = this.projectName.substring(0, this.projectName.length() - 4);
        File assetsDir = new File(getAssetsDirPath());
        assetsDir.mkdir();
        this.sceneManager = sceneManager;
    }

    public String getPath() {
        return getProjectDir() + File.separator + getName() + ".n2d";
    }

    public String getProjectDir() {
        return projectDir;
    }

    public String getName() {
        return projectName;
    }

    public String getAssetsDirPath() {
        return getProjectDir() + File.separator + "Assets";
    }

    public void saveProject() throws IOException {
        try (FullBufferedWriter fw = new FullBufferedWriter(new FileWriter(getPath()))) {
            fw.write(sceneManager.getCurrentScene().getName());
            fw.writeIntLine(sceneManager.getSceneCount());
            for (Scene scene : sceneManager.getSceneList())
                new SceneSaver(scene).save(fw);
        }
    }

    public boolean containsSceneWithName(String name) {
        for (Scene scene : sceneManager.getSceneList()) {
            if (scene.getName().equals(name))
                return true;
        }

        return false;
    }

    public void build(String startScene, FileHandle sceneFileOut, FileHandle assetsFileOut,
                      BuildProgressUpdateListener listener) throws  IOException {
        StringWriter sceneStrWriter = new StringWriter();
        StringWriter assetsStrWriter = new StringWriter();
        XmlWriter sceneXmlWriter = new XmlWriter(sceneStrWriter);
        XmlWriter assetsXmlWriter = new XmlWriter(assetsStrWriter);

        sceneXmlWriter.element("project").
                attribute("name", projectName).
                attribute("startScene", startScene);
        assetsXmlWriter.element("assets");

        for (int i = 0; i < sceneManager.getSceneCount(); ++i) {
            Scene scene = sceneManager.getSceneList().get(i);
            new SceneBuilder(scene).build(sceneXmlWriter, assetsXmlWriter);
            sceneXmlWriter.pop();
            listener.onBuildProgressUpdate(scene, i, sceneManager.getSceneCount());
        }
        sceneXmlWriter.pop();
        assetsXmlWriter.pop();

        sceneFileOut.writeString(sceneStrWriter.toString(), false);
        assetsFileOut.writeString(assetsStrWriter.toString(), false);

        listener.onProjectCompiled();
    }

    public String getTempDir() {
        return PlatformUtil.pathJoin(projectDir, "tmp");
    }

    public boolean ensureTempDir() {
        File tmpDir = new File(getTempDir());
        return tmpDir.exists() || tmpDir.mkdir();
    }
}
