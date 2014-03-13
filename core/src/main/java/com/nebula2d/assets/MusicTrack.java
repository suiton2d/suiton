package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * MusicTrack is an {@link com.nebula2d.assets.Asset} for the
 * playback of music files. MusicTracks are streamed.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class MusicTrack extends Asset {

    private Music track;

    public MusicTrack(String path) {
        super(path);
    }

    public Music getTrack() {
        return track;
    }

    /**
     * Plays the MusicSource.
     */
    public void play() {
        track.play();
    }

    /**
     * Stops playback of the MusicSource.
     */
    public void stop() {
        track.stop();
    }

    /**
     * Pauses playback of the MusicSource.
     */
    public void pause() {
        track.pause();
    }

    public boolean isLooping() {
        return track.isLooping();
    }

    public void setLooping(boolean looping) {
        track.setLooping(looping);
    }

    @Override
    protected void onLoad() {
        track = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    @Override
    protected void onUnload() {
        track.dispose();
        track = null;
    }
}
