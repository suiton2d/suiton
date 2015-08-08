package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.Script;
import com.suiton2d.components.Behavior;
import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class BehaviorLoader extends BaseComponentLoader {

    public BehaviorLoader(Scene scene) {
        super(scene);
    }

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        Behavior behavior = new Behavior();
        String filename = fr.readLine();
        behavior.setFilename(filename);
        AssetManager.addAsset(scene.getName(), new AssetDescriptor<>(filename, Script.class));
        return behavior;
    }
}
