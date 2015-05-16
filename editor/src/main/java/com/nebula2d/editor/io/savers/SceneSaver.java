package com.nebula2d.editor.io.savers;

import com.nebula2d.editor.io.FullBufferedWriter;
import com.nebula2d.editor.io.Saver;
import com.nebula2d.scene.Layer;
import com.nebula2d.scene.Scene;

import java.io.IOException;

public class SceneSaver implements Saver {

    private Scene scene;

    public SceneSaver(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(scene.getName());
        fw.writeIntLine(scene.getLayers().size());
        for (Layer layer : scene.getLayers()) {
            new LayerSaver(layer).save(fw);
        }

        fw.writeFloatLine(scene.getGravity().x);
        fw.writeFloatLine(scene.getGravity().y);
    }
}
