package com.suiton2d.editor.io.savers;

import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;

import java.io.IOException;

public class LayerSaver implements Saver {

    private Layer layer;

    public LayerSaver(Layer layer) {
        this.layer = layer;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(layer.getName());
        fw.writeIntLine(layer.getZOrder());
        fw.writeIntLine(layer.getGameObjects().size());
        for (GameObject gameObject : layer.getGameObjects()) {
            new GameObjectSaver(gameObject).save(fw);
        }
    }
}
