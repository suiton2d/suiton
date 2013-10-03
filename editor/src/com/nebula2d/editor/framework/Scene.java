package com.nebula2d.editor.framework;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scene implements ISaveable, ILoadable {

    //region members
    protected String name;
    protected int id;

    protected List<Layer> layers;
    //endregion

    //region constructors
    public Scene(String name) {
        this.name = name;
        id = 0;
        layers = new ArrayList<Layer>();
    }
    //endregion

    //region accessors
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addLayer(Layer l) {
        layers.add(l);
    }

    public void removeLayer(Layer l) {
        layers.remove(l);
    }

    public void removeLayer(int idx) {
        layers.remove(idx);
    }

    public Layer getLayer(String name) {
        for (Layer l : layers) {
            if (l.getName().equals(name)) {
                return l;
            }
        }

        return null;
    }
    //endregion

    //region public methods
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        for (Layer l : layers) {
            l.render(selectedObject, batcher, cam);
        }
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        name = fr.readLine();
        int size = fr.readIntLine();

        for (int i = 0; i < size; ++i) {
            Layer layer = new Layer("tmp");
            layer.load(fr);
            addLayer(layer);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeIntLine(layers.size());

        for (Layer l : layers) {
            l.save(fw);
        }
    }
    //endregion
}
