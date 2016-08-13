package com.suiton2d.editor.io.loaders;

import com.suiton2d.assets.AssetManager;
import com.suiton2d.components.gfx.Renderer;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class RendererLoader implements Loader<Renderer> {

    private Scene scene;
    private AssetManager assetManager;

    public RendererLoader(Scene scene, AssetManager assetManager) {
        this.scene = scene;
        this.assetManager = assetManager;
    }

    @Override
    public Renderer load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        Types.RendererType rendererType = Types.RendererType.valueOf(fr.readLine());
        Loader<Renderer> loader;
        switch (rendererType) {
            case TILED:
                loader = new TiledMapRendererLoader(scene, name, assetManager);
                break;
            case SPRITE:
                loader = new SpriteRendererLoader(scene, name, assetManager);
                break;
            default:
                return null;
        }

        return loader.load(fr);
    }
}
