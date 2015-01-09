package com.nebula2d.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.nebula2d.assets.Sprite;

public class SpriteLoader extends AsynchronousAssetLoader<Sprite, SpriteLoader.SpriteParameter> {

    private TextureLoader textureLoader;

    public SpriteLoader(FileHandleResolver resolver) {
        super(resolver);
        this.textureLoader = new TextureLoader(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SpriteParameter parameter) {
        textureLoader.loadAsync(manager, fileName, file, parameter.textureParameter);
    }

    @Override
    public Sprite loadSync(AssetManager manager, String fileName, FileHandle file, SpriteParameter parameter) {
        Texture texture = textureLoader.loadSync(manager, fileName, file, parameter.textureParameter);
        return texture != null ? new Sprite(file.path(), texture) : null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SpriteParameter parameter) {
        return null;
    }

    public static class SpriteParameter extends AssetLoaderParameters<Sprite> {
        public TextureLoader.TextureParameter textureParameter;
    }
}
