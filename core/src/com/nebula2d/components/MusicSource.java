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

    public void play() {
        musicTrack.play();
    }

    public void stop() {
        musicTrack.stop();
    }

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

    public void setOnCompletionListener(Music.OnCompletionListener listener) {
        musicTrack.getTrack().setOnCompletionListener(listener);
    }
}
