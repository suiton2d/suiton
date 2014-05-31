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

public class SoundEffect extends Asset {

    //LibGDX Sound doesn't support on completion listeners which we need for GUI, so we will use Music here.
    private com.badlogic.gdx.audio.Music sound;
    private boolean loop;

    public SoundEffect(final String path) {
        super(path);

    }

    @Override
    public void initialize() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                sound = Gdx.audio.newMusic(new FileHandle(new File(path)));
                isLoaded = true;
            }
        });
    }

    @Override
    public void dispose() {
        if (isLoaded) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    sound.dispose();
                    sound = null;
                    isLoaded = false;
                }
            });
        }
    }

    public void play() {
        sound.play();
    }

    public boolean isLooping() {
        return sound.isLooping();
    }

    public void setLoop(boolean loop) {
        sound.setLooping(loop);
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        loop = fr.readBooleanLine();
        sound.setVolume(fr.readFloatLine());
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeBoolLine(loop);
        fw.writeFloatLine(sound.getVolume());
    }

    public void setOnCompleteListener(com.badlogic.gdx.audio.Music.OnCompletionListener listener) {
        sound.setOnCompletionListener(listener);
    }
}
