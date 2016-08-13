package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class SceneLoader implements Loader<Scene> {

    private AssetManager assetManager;
    private World world;

    public SceneLoader(AssetManager assetManager, World world) {
        this.assetManager = assetManager;
        this.world = world;
    }

    @Override
    public Scene load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        float gx = fr.readFloatLine();
        float gy = fr.readFloatLine();
        boolean sleepPhysics = fr.readBooleanLine();
        Scene scene = new Scene(name, new Vector2(gx, gy), sleepPhysics);
        int numLayers = fr.readIntLine();
        LayerLoader layerLoader = new LayerLoader(scene, assetManager, world);
        for (int i = 0; i < numLayers; ++i) {
            Layer layer = layerLoader.load(fr);
            scene.addLayer(layer);
        }

        return scene;
    }
}
