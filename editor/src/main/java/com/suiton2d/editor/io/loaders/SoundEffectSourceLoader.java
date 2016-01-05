package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.SoundEffect;
import com.suiton2d.assets.loaders.SoundEffectLoader;
import com.suiton2d.components.Component;
import com.suiton2d.components.SoundEffectSource;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class SoundEffectSourceLoader extends BaseComponentLoader {

    public SoundEffectSourceLoader(Scene scene) {
        super(scene);
    }

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        String filename =fr.readLine();
        SoundEffectLoader.SoundEffectParameter parameter = new SoundEffectLoader.SoundEffectParameter();
        parameter.soundParameter = new SoundLoader.SoundParameter();
        AssetManager.addAsset(scene.getName(), new AssetDescriptor<>(filename, SoundEffect.class, parameter));
        return new SoundEffectSource(getName(), filename);
    }
}
