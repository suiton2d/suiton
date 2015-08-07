package com.suiton2d.editor.io.savers;

import com.badlogic.gdx.scenes.scene2d.Actor;
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
        fw.writeIntLine(layer.getChildren().size);
        for (Actor gameObject : layer.getChildren()) {
            new GameObjectSaver((GameObject)gameObject).save(fw);
        }
    }
}
