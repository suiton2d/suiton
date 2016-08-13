package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.SoundEffect;
import com.suiton2d.assets.loaders.SoundEffectLoader;
import com.suiton2d.components.audio.SoundEffectSource;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class SoundEffectSourceLoader implements Loader<SoundEffectSource> {

    private Scene scene;
    private AssetManager assetManager;

    public SoundEffectSourceLoader(Scene scene, AssetManager assetManager) {
        this.scene = scene;
        this.assetManager = assetManager;
    }

    @Override
    public SoundEffectSource load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        String filename = fr.readLine();
        SoundEffectLoader.SoundEffectParameter parameter = new SoundEffectLoader.SoundEffectParameter();
        parameter.soundParameter = new SoundLoader.SoundParameter();
        assetManager.registerAsset(scene.getName(), new AssetDescriptor<>(filename, SoundEffect.class, parameter));
        return new SoundEffectSource(name, filename, assetManager);
    }
}
