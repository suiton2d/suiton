package com.suiton2d.editor.io.loaders;

import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;

import java.io.IOException;

public class LayerLoader implements Loader<Layer> {

    @Override
    public Layer load(FullBufferedReader fr) throws IOException {
        Layer layer = new Layer(fr.readLine());
        layer.setZOrder(fr.readIntLine());
        int numGameObjects = fr.readIntLine();
        GameObjectLoader gameObjectLoader = new GameObjectLoader();
        for (int i = 0; i < numGameObjects; ++i) {
            GameObject gameObject = gameObjectLoader.load(fr);
            layer.addActor(gameObject);
        }

        return layer;
    }
}
