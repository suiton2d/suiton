package com.nebula2d.components;

import com.badlogic.gdx.audio.Music;
import com.nebula2d.assets.MusicTrack;

/**
 * MusicSource is a {@link com.nebula2d.components.Component} used for the playback of
 * music files.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class MusicSource extends Component {

    private MusicTrack musicTrack;

    public MusicSource(String name, String trackPath) {
        super(name);
        musicTrack = new MusicTrack(trackPath);
    }

    /**
     * Plays the {@link com.nebula2d.assets.MusicTrack}.
     */
    public void play() {
        musicTrack.play();
    }

    /**
     * Stops the MusicTrack.
     */
    public void stop() {
        musicTrack.stop();
    }

    /**
     * Pauses the MusicTrack.
     */
    public void pause() {
        musicTrack.pause();
    }

    public boolean isLooping() {
        return musicTrack.isLooping();
    }

    public void setLooping(boolean looping) {
        musicTrack.setLooping(looping);
    }

    public float getVolume() {
        return musicTrack.getTrack().getVolume();
    }

    public void setVolume(float volume) {
        musicTrack.getTrack().setVolume(volume);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    /**
     * Sets a callback that is fired whenever MusicTrack playback completes.
     * @param listener The callback that will be fired on playback completion.
     */
    public void setOnCompletionListener(Music.OnCompletionListener listener) {
        musicTrack.getTrack().setOnCompletionListener(listener);
    }
}
