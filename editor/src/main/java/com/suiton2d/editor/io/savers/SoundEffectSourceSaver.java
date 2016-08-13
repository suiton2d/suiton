package com.suiton2d.editor.io.savers;

import com.suiton2d.components.audio.SoundEffectSource;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class SoundEffectSourceSaver implements Saver {

    private SoundEffectSource soundEffectSource;

    public SoundEffectSourceSaver(SoundEffectSource soundEffectSource) {
        this.soundEffectSource = soundEffectSource;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.SFX.name());
        fw.writeLine(soundEffectSource.getName());
        fw.writeLine(soundEffectSource.getFilename());
    }
}
