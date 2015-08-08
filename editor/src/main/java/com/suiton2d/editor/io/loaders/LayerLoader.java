package com.suiton2d.editor.io.loaders;

import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class LayerLoader implements Loader<Layer> {

    private Scene scene;

    public LayerLoader(Scene scene) {
        this.scene = scene;
    }

    @Override
    public Layer load(FullBufferedReader fr) throws IOException {
        Layer layer = new Layer(fr.readLine());
        layer.setZOrder(fr.readIntLine());
        int numGameObjects = fr.readIntLine();
        GameObjectLoader gameObjectLoader = new GameObjectLoader(scene);
        for (int i = 0; i < numGameObjects; ++i) {
            GameObject gameObject = gameObjectLoader.load(fr);
            layer.addActor(gameObject);
        }

        return layer;
    }
}
