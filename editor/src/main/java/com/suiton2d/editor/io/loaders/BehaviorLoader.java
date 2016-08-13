package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.Script;
import com.suiton2d.components.behavior.Behavior;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class BehaviorLoader implements Loader<Behavior> {

    private Scene scene;
    private AssetManager assetManager;

    public BehaviorLoader(Scene scene, AssetManager assetManager) {
        this.scene = scene;
        this.assetManager = assetManager;
    }

    @Override
    public Behavior load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        String filename = fr.readLine();
        assetManager.registerAsset(scene.getName(), new AssetDescriptor<>(filename, Script.class));
        return new Behavior(name, filename, assetManager);
    }
}
