package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.math.Vector2;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class SceneLoader implements Loader<Scene> {

    @Override
    public Scene load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        float gx = fr.readFloatLine();
        float gy = fr.readFloatLine();
        boolean sleepPhysics = fr.readBooleanLine();
        Scene scene = new Scene(name, new Vector2(gx, gy), sleepPhysics);
        int numLayers = fr.readIntLine();
        for (int i = 0; i < numLayers; ++i) {
            Layer layer = new LayerLoader().load(fr);
            scene.addLayer(layer);
        }

        return scene;
    }
}
