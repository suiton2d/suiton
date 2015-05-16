package com.nebula2d.editor.io.loaders;

import com.nebula2d.components.Component;
import com.nebula2d.components.SoundEffectSource;
import com.nebula2d.editor.io.FullBufferedReader;

import java.io.IOException;

public class SoundEffectSourceLoader extends BaseComponentLoader {

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        SoundEffectSource soundEffectSource = new SoundEffectSource();
        soundEffectSource.setFilename(fr.readLine());
        return soundEffectSource;
    }
}
