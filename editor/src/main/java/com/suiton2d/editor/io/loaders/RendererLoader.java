package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class RendererLoader extends BaseComponentLoader {

    public RendererLoader(Scene scene) {
        super(scene);
    }

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        Types.RendererType rendererType = Types.RendererType.valueOf(fr.readLine());
        Loader<Component> loader;
        switch (rendererType) {
            case TILED:
                loader = new TiledMapRendererLoader(scene, getName());
                break;
            case SPRITE:
                loader = new SpriteRendererLoader(scene, getName());
                break;
            default:
                return null;
        }

        return loader.load(fr);
    }
}
