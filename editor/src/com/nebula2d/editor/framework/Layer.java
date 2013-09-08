package com.nebula2d.editor.framework;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Layer implements ISaveable, ILoadable {

    //region members
    protected String name;

    protected List<GameObject> gameObjects;
    //endregion

    //region constructors
    public Layer(String name) {
        this.name = name;
        gameObjects = new ArrayList<GameObject>();
    }
    //endregion

    //region accessors
    public String getName() {
        return name;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public void removeGameObject(GameObject go) {
        gameObjects.remove(go);
    }

    public void removeGameObject(int idx) {
        gameObjects.remove(idx);
    }

    public GameObject getGameObject(String name) {
        for (GameObject go : gameObjects) {
            if (go.getName().equals(name)) {
                return go;
            }
        }

        return null;
    }

    public GameObject getGameObject(int idx) {
        return gameObjects.get(idx);
    }
    //endregion

    //region public methods
    public void render(GameObject selectedObject, SpriteBatch batcher) {
        for (GameObject go : gameObjects) {
            go.render(selectedObject, batcher);
        }
    }

    @Override
    public String toString() {
        return name;
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        name = fr.readLine();
        int size = fr.readIntLine();
        for (int i = 0; i < size; ++i) {
            GameObject go = new GameObject("tmp");
            go.load(fr);
            addGameObject(go);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeIntLine(gameObjects.size());
        for (GameObject go : gameObjects) {
            go.save(fw);
        }
    }
    //endregion
}
