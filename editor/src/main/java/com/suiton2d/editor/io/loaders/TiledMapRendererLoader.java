package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.components.TiledMapRenderer;
import com.suiton2d.editor.io.FullBufferedReader;

import java.io.IOException;

public class TiledMapRendererLoader extends BaseComponentLoader {

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        TiledMapRenderer tiledMapRenderer = new TiledMapRenderer();
        tiledMapRenderer.setFilename(fr.readLine());

        return tiledMapRenderer;
    }
}
