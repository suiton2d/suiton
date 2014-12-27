package com.nebula2d.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.nebula2d.assets.SoundEffect;


public class SoundEffectLoader extends AsynchronousAssetLoader<SoundEffect, SoundEffectLoader.SoundEffectParameter> {

    private SoundLoader soundLoader;

    public SoundEffectLoader(FileHandleResolver resolver) {
        super(resolver);
        this.soundLoader = new SoundLoader(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SoundEffectParameter parameter) {
        soundLoader.loadAsync(manager, fileName, file, parameter.soundParameter);
    }

    @Override
    public SoundEffect loadSync(AssetManager manager, String fileName, FileHandle file, SoundEffectParameter parameter) {
        Sound sound = soundLoader.loadSync(manager, fileName, file, parameter.soundParameter);
        return sound != null ? new SoundEffect(file.path(), sound) : null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SoundEffectParameter parameter) {
        return null;
    }

    public static class SoundEffectParameter extends AssetLoaderParameters<SoundEffect> {

        public SoundLoader.SoundParameter soundParameter;
    }
}
