package com.suiton2d.editor.io.savers;

import com.suiton2d.components.TiledMapRenderer;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class TiledMapRendererSaver extends BaseComponentSaver<TiledMapRenderer> {

    public TiledMapRendererSaver(TiledMapRenderer component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.RendererType.TILED.name());
        fw.writeLine(getComponent().getFilename());
    }
}
