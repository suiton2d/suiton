package com.nebula2d.editor.io.savers;

import com.nebula2d.components.MusicSource;
import com.nebula2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class MusicSourceSaver extends  BaseComponentSaver<MusicSource> {

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
