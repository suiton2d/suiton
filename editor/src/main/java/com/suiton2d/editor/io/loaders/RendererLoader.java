package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.components.Renderer;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class RendererLoader implements Loader<Component>  {

    private Scene scene;

    public RendererLoader(Scene scene) {
        this.scene = scene;
    }

    @Override
    public Component load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        Types.RendererType rendererType = Types.RendererType.valueOf(fr.readLine());
        Loader<Component> loader;
        switch (rendererType) {
            case TILED:
                loader = new TiledMapRendererLoader(scene);
                break;
            case SPRITE:
                loader = new SpriteRendererLoader(scene);
                break;
            default:
                return null;
        }

        Component component = loader.load(fr);
        component.setName(name);
        return component;
    }
}
