package com.suiton2d.components;

import com.badlogic.gdx.audio.Music;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.MusicTrack;
import com.suiton2d.scene.GameObject;

/**
 * MusicSource is a {@link Component} used for the playback of
 * music files.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class MusicSource extends Component {

    private String filename;

    public MusicSource() {}

    public MusicSource(String name, String filename) {
        super(name);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public MusicTrack getMusicTrack() {
        return AssetManager.getAsset(filename, MusicTrack.class);
    }
    /**
     * Plays the {@link MusicTrack}.
     */
    public void play() {
        getMusicTrack().play();
    }

    /**
     * Stops the MusicTrack.
     */
    public void stop() {
        getMusicTrack().stop();
    }

    /**
     * Pauses the MusicTrack.
     */
    public void pause() {
        getMusicTrack().pause();
    }

    public boolean isLooping() {
        return getMusicTrack().isLooping();
    }

    public void setLooping(boolean looping) {
        getMusicTrack().setLooping(looping);
    }

    public float getVolume() {
        return getMusicTrack().getData().getVolume();
    }

    public void setVolume(float volume) {
        getMusicTrack().getData().setVolume(volume);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }

    /**
     * Sets a callback that is fired whenever MusicTrack playback completes.
     * @param listener The callback that will be fired on playback completion.
     */
    public void setOnCompletionListener(Music.OnCompletionListener listener) {
        getMusicTrack().getData().setOnCompletionListener(listener);
    }
}
