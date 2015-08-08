package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.TiledTileSheet;
import com.suiton2d.assets.loaders.TiledTileSheetLoader;
import com.suiton2d.components.Component;
import com.suiton2d.components.TiledMapRenderer;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class TiledMapRendererLoader implements Loader<Component> {

    private Scene scene;

    public TiledMapRendererLoader(Scene scene) {
        this.scene = scene;
    }

    @Override
    public Component load(FullBufferedReader fr) throws IOException {
        TiledMapRenderer tiledMapRenderer = new TiledMapRenderer();
        String filename = fr.readLine();
        tiledMapRenderer.setFilename(filename);
        TiledTileSheetLoader.TiledTileSheetParameter parameter = new TiledTileSheetLoader.TiledTileSheetParameter();
        parameter.tmxParameters = new TmxMapLoader.Parameters();
        AssetManager.addAsset(scene.getName(), new AssetDescriptor<>(filename, TiledTileSheet.class, parameter));
        return tiledMapRenderer;
    }
}
