package com.suiton2d.components;

import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.SoundEffect;
import com.suiton2d.scene.GameObject;

/**
 * SoundEffectSource is a {@link Component} used for the
 * playback of sound effects.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SoundEffectSource extends Component {

    private String filename;

    public SoundEffectSource() {}

    public SoundEffectSource(String name, String filename) {
        super(name);
        this.filename = filename;
    }

    public SoundEffect getSoundEffect() {
        return AssetManager.getAsset(filename, SoundEffect.class);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Plays the {@link SoundEffect}.
     * @return The sound ID associated with the playback instance
     */
    public long play() {
        return getSoundEffect().play();
    }

    /**
     * Plays the SoundEffect in a loop.
     * @return The sound ID associated with the playback instance
     */
    public long loop() {
        return getSoundEffect().loop();
    }

    /**
     * Stops a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to stop.
     */
    public void stop(long soundId) {
        getSoundEffect().stop(soundId);
    }

    /**
     * Pauses a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to pause.
     */
    public void pause(long soundId) {
        getSoundEffect().pause(soundId);
    }

    /**
     * Resumes a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to resume.
     */
    public void resume(long soundId) {
        getSoundEffect().resume(soundId);
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
}
