package com.nebula2d.editor.io.loaders;

import com.nebula2d.components.Component;
import com.nebula2d.components.TiledMapRenderer;
import com.nebula2d.editor.io.FullBufferedReader;

import java.io.IOException;

public class TiledMapRendererLoader extends BaseComponentLoader {

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        TiledMapRenderer tiledMapRenderer = new TiledMapRenderer();
        tiledMapRenderer.setFilename(fr.readLine());

        return tiledMapRenderer;
    }
}
