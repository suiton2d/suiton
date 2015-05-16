package com.nebula2d.editor.io.savers;

import com.nebula2d.components.TiledMapRenderer;
import com.nebula2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class TiledMapRendererSaver extends BaseComponentSaver<TiledMapRenderer> {

    public TiledMapRendererSaver(TiledMapRenderer component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
