package com.suiton2d.editor.io.savers;

import com.suiton2d.components.SoundEffectSource;
import com.suiton2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class SoundEffectSourceSaver extends BaseComponentSaver<SoundEffectSource> {

    public SoundEffectSourceSaver(SoundEffectSource component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
