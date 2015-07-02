package com.suiton2d.editor.io.savers;

import com.suiton2d.components.MusicSource;
import com.suiton2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class MusicSourceSaver extends  BaseComponentSaver<MusicSource> {

    public MusicSourceSaver(MusicSource component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
