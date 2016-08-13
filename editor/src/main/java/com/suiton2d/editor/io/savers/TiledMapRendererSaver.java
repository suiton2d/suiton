package com.suiton2d.editor.io.savers;

import com.suiton2d.components.gfx.TiledMapRenderer;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class TiledMapRendererSaver implements Saver {

    private TiledMapRenderer renderer;

    public TiledMapRendererSaver(TiledMapRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.RENDER.name());
        fw.writeLine(renderer.getName());
        fw.writeLine(Types.RendererType.TILED.name());
        fw.writeLine(renderer.getFilename());
    }
}
