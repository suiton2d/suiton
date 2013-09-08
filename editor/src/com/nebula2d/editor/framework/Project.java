package com.nebula2d.editor.framework;

import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.*;
import java.util.List;

public class Project implements ISaveable, ILoadable{

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

        for (Layer layer : scene.getLayers()) {
            //TODO:: Construct scene graph
        }
    }

    public void loadScene(String name) {
        Scene scene = getScene(name);
        if (scene == null)
            return;

        for (Layer layer : scene.getLayers()) {
            //TODO: Construct scene graph
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
