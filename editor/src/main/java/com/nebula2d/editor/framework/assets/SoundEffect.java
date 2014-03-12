/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.framework.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.File;
import java.io.IOException;

public class SoundEffect extends AbstractSound {

    //region members
    //LibGDX Sound doesnt support on completion listeners which we need for GUI, so we will use Music here.
    private com.badlogic.gdx.audio.Music sound;
    private boolean isPlaying;
    private boolean isPaused;
    private boolean loop;
    private float volume;
    //endregion

    //region constructor
    public SoundEffect(String path) {
        super(path);
        sound = Gdx.audio.newMusic(new FileHandle(new File(path)));
        isPlaying = isPaused = loop = false;
    }
    //endregion

    //region playback controls
    public void play() {
        sound.play();
    }

    public void stop() {
        sound.stop();
    }

    public void pause() {
        sound.pause();
    }

    public boolean isLooping() {
        return sound.isLooping();
    }

    public void setLoop(boolean loop) {
        sound.setLooping(loop);
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        loop = fr.readBooleanLine();
        volume = fr.readFloatLine();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeBoolLine(loop);
        fw.writeFloatLine(volume);
    }

    public void setOnCompleteListener(com.badlogic.gdx.audio.Music.OnCompletionListener listener) {
        sound.setOnCompletionListener(listener);
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float v) {
        this.volume = v;
        sound.setVolume(v);
    }
    //endregion
}
