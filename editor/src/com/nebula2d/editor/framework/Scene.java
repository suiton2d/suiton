package com.nebula2d.editor.framework;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Scene extends BaseSceneNode implements ISaveable, ILoadable {

    //region members
    protected int id;
    //endregion

    //region constructors
    public Scene(String name) {
        super(name);
        id = 0;
    }
    //endregion

    //region accessors
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Layer getLayer(String name) {
        Enumeration children = children();
        while (children.hasMoreElements()) {
            Layer layer = (Layer) children.nextElement();
            if (layer.getName().equals(name))
                return layer;
        }

        return null;
    }
    //endregion

    //region public methods
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        Enumeration layers = children();

        while (layers.hasMoreElements()) {
            ((Layer) layers.nextElement()).render(selectedObject, batcher, cam);
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
            add(layer);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeIntLine(getChildCount());

        Enumeration layers = children();
        while (layers.hasMoreElements())
            ((Layer) layers.nextElement()).save(fw);
    }
    //endregion
}
