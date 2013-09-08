package com.nebula2d.editor.framework.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.File;
import java.io.IOException;

public class Music extends Asset {

    //region members
    private com.badlogic.gdx.audio.Music music;

    private float volume;
    //endregion

    //region constructor
    public Music(String path) {
        super(path);
        music = Gdx.audio.newMusic(new FileHandle(new File(path)));
        music.setVolume(volume);
    }
    //endregion

    //region playback controls
    public void play() {
        music.play();
    }

    public void stop() {
        music.stop();
    }

    public void pause() {
        music.pause();
    }
    //endregion

    //region accessors
    public boolean isLooping() {
        return music.isLooping();
    }

    public void setLooping(boolean loop) {
        music.setLooping(loop);
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float v) {
        this.volume = v;
        music.setVolume(v);
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        volume = fr.readFloatLine();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeFloatLine(volume);
    }
    //endregion
}
