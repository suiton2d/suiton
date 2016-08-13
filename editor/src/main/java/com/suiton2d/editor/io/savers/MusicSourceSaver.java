package com.suiton2d.editor.io.savers;

import com.suiton2d.components.audio.MusicSource;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class MusicSourceSaver implements Saver {

    private MusicSource musicSource;

    public MusicSourceSaver(MusicSource musicSource) {
        this.musicSource = musicSource;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.MUSIC.name());
        fw.writeLine(musicSource.getName());
        fw.writeLine(musicSource.getFilename());
    }
}
