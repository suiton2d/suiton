package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.TiledTileSheet;
import com.suiton2d.assets.loaders.TiledTileSheetLoader;
import com.suiton2d.components.gfx.Renderer;
import com.suiton2d.components.gfx.TiledMapRenderer;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class TiledMapRendererLoader implements Loader<Renderer> {

    private Scene scene;
    private String name;
    private AssetManager assetManager;

    public TiledMapRendererLoader(Scene scene, String name, AssetManager assetManager) {
        this.scene = scene;
        this.name = name;
        this.assetManager = assetManager;
    }

    @Override
    public Renderer load(FullBufferedReader fr) throws IOException {
        String filename = fr.readLine();
        TiledTileSheetLoader.TiledTileSheetParameter parameter = new TiledTileSheetLoader.TiledTileSheetParameter();
        parameter.tmxParameters = new TmxMapLoader.Parameters();
        assetManager.registerAsset(scene.getName(), new AssetDescriptor<>(filename, TiledTileSheet.class, parameter));
        return new TiledMapRenderer(name, filename, scene, assetManager);
    }
}
