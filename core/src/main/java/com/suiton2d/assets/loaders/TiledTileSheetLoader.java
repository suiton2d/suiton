package com.suiton2d.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.suiton2d.assets.TiledTileSheet;

public class TiledTileSheetLoader extends AsynchronousAssetLoader<TiledTileSheet,
        TiledTileSheetLoader.TiledTileSheetParameter> {

    private TmxMapLoader tmxMapLoader;
    public TiledTileSheetLoader(FileHandleResolver resolver) {
        super(resolver);
        this.tmxMapLoader = new TmxMapLoader(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TiledTileSheetParameter parameter) {
        tmxMapLoader.loadAsync(manager, fileName, file, parameter.tmxParameters);
    }

    @Override
    public TiledTileSheet loadSync(AssetManager manager, String fileName, FileHandle file, TiledTileSheetParameter parameter) {
        TiledMap tiledMap = tmxMapLoader.loadSync(manager, fileName, file, parameter.tmxParameters);
        return tiledMap != null ? new TiledTileSheet(file.path(), tiledMap) : null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TiledTileSheetParameter parameter) {
        return null;
    }

    public static class TiledTileSheetParameter extends AssetLoaderParameters<TiledTileSheet> {
        public TmxMapLoader.Parameters tmxParameters;
    }
}
