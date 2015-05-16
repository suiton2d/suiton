package com.nebula2d.editor.io.savers;

import com.nebula2d.components.SoundEffectSource;
import com.nebula2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class SoundEffectSourceSaver extends BaseComponentSaver<SoundEffectSource> {

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
