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
import com.nebula2d.assets.SpriteSheet;


public class SpriteSheetLoader extends AsynchronousAssetLoader<SpriteSheet, SpriteSheetLoader.SpriteSheetParamter> {

    private TextureLoader textureLoader;

    public SpriteSheetLoader(FileHandleResolver resolver) {
        super(resolver);
        this.textureLoader = new TextureLoader(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SpriteSheetParamter parameter) {
        textureLoader.loadAsync(manager, fileName, file, parameter.textureParameter);
    }

    @Override
    public SpriteSheet loadSync(AssetManager manager, String fileName, FileHandle file, SpriteSheetParamter parameter) {
        Texture texture = textureLoader.loadSync(manager, fileName, file, parameter.textureParameter);
        return texture != null ? new SpriteSheet(file.path(), texture,
                parameter.frameWidth, parameter.frameHeight) : null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SpriteSheetParamter parameter) {
        return null;
    }

    public static class SpriteSheetParamter extends AssetLoaderParameters<SpriteSheet> {
        public TextureLoader.TextureParameter textureParameter;
        public int frameHeight;
        public int frameWidth;
    }
}
