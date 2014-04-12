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

package com.nebula2d.editor.framework;

import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.ui.SceneGraph;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Project implements ISerializable {

    //region members
    private List<Scene> scenes;
    private int currentSceneIdx;
    private String projectDir;
    private String projectName;
    //endregion

    //region constructors
    public Project(String dir, String name) {
        this.projectDir = dir;
        this.projectName = name;
        this.currentSceneIdx = 0;
        scenes = new ArrayList<Scene>();
    }
    //endregion

    //region accessors
    public String getProjectName() {
        return this.projectName;
    }

    public String getProjectDir() {
        return this.projectDir;
    }

    public List<Scene> getScenes() {
        return this.scenes;
    }

    public int getCurrentSceneIdx() {
        return this.currentSceneIdx;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void removeScene(Scene scene) {
        scenes.remove(scene);
    }

    public void removeScene(int idx) {
        scenes.remove(idx);
    }

    public Scene getScene(String name) {
        for (Scene scene : scenes) {
            if (scene.getName().equals(name))
                return scene;
        }

        return null;
    }

    public Scene getScene(int idx) {
        return scenes.get(idx);
    }

    public Scene getCurrentScene() {

        if (scenes.isEmpty())
            return null;

        return scenes.get(currentSceneIdx);
    }

    public void setCurrentScene(String name) {
        for (int i = 0; i < scenes.size(); ++i) {
            if (scenes.get(i).getName().equals(name)) {
                currentSceneIdx = i;
                loadScene(i);
            }
        }
    }

    public void setCurrentScene(int idx) {
        currentSceneIdx = idx;
        loadScene(idx);
    }

    public String getPath() {
        return projectDir + File.separator + projectName;
    }

    public String getNameWithoutExt() {
        int dot = projectName.indexOf('.');
        if (dot == -1)
            return projectName;

        String ext = projectName.substring(dot);

        if (!ext.equals(".n2d"))
            return projectName;

        return projectName.substring(0, projectName.length() - ext.length());
    }
    //endregion

    //region public methods
    public void loadProject() throws IOException {
        scenes.clear();
        FullBufferedReader fr = new FullBufferedReader(new FileReader(getPath()));

        load(fr);
        loadExtraAssets(fr);

        loadScene(currentSceneIdx);
    }

    public void saveProject() throws IOException {
        FullBufferedWriter fw = new FullBufferedWriter(new FileWriter(getPath()));
        save(fw);
        saveExtraAssets(fw);
    }

    public boolean saveExtraAssets(FullBufferedWriter fw) throws IOException {
        //TODO: Implement
        return true;
    }

    public boolean loadExtraAssets(FullBufferedReader fr) throws IOException {
        //TODO: Implement
        return true;
    }

    public void loadScene(int idx) {
        Scene scene = getScene(idx);

        if (scene == null)
            return;

        loadScene(scene);
    }

    public void loadScene(String name) {
        Scene scene = getScene(name);
        if (scene == null)
            return;

        loadScene(scene);
    }

    private void loadScene(Scene scene) {
        SceneGraph graph = MainFrame.getSceneGraph();
        Enumeration layers = scene.children();
        while (layers.hasMoreElements()) {
            graph.addLayer((Layer) layers.nextElement());
        }
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        int size = fr.readIntLine();

        for (int i = 0; i < size; ++i) {
            Scene s = new Scene("tmp");
            s.load(fr);
            addScene(s);
        }

        currentSceneIdx = fr.readIntLine();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeIntLine(scenes.size());

        for (Scene scene : scenes) {
            scene.save(fw);
        }

        fw.writeIntLine(currentSceneIdx);
    }
    //endregion
}
