package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.components.MusicSource;
import com.suiton2d.editor.io.FullBufferedReader;

import java.io.IOException;

public class MusicSourceLoader extends BaseComponentLoader {

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        MusicSource musicSource = new MusicSource();
        musicSource.setFilename(fr.readLine());
        return musicSource;
    }
}
