package com.nebula2d.assets;

import com.badlogic.gdx.audio.Music;

/**
 * MusicTrack is an {@link com.nebula2d.assets.Asset} for the
 * playback of music files. MusicTracks are streamed.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class MusicTrack extends Asset<Music> {


    public MusicTrack(String path, Music track) {
        super(path, track);
    }

    /**
     * Plays the MusicSource.
     */
    public void play() {
        data.play();
    }

    /**
     * Stops playback of the MusicSource.
     */
    public void stop() {
        data.stop();
    }

    /**
     * Pauses playback of the MusicSource.
     */
    public void pause() {
        data.pause();
    }

    public boolean isLooping() {
        return data.isLooping();
    }

    public void setLooping(boolean looping) {
        data.setLooping(looping);
    }
}
