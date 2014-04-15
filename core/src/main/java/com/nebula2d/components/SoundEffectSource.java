package com.nebula2d.components;

import com.nebula2d.assets.SoundEffect;

/**
 * SoundEffectSource is a {@link com.nebula2d.components.Component} used for the
 * playback of sound effects.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SoundEffectSource extends Component {

    private SoundEffect soundEffect;

    public SoundEffectSource(String name, String soundEffectPath) {
        super(name);
        soundEffect = new SoundEffect(soundEffectPath);
    }

    /**
     * Plays the {@link com.nebula2d.assets.SoundEffect}.
     * @return The sound ID associated with the playback instance
     */
    public long play() {
        return soundEffect.play();
    }

    /**
     * Plays the SoundEffect in a loop.
     * @return The sound ID associated with the playback instance
     */
    public long loop() {
        return soundEffect.loop();
    }

    /**
     * Stops a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to stop.
     */
    public void stop(long soundId) {
        soundEffect.stop(soundId);
    }

    /**
     * Pauses a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to pause.
     */
    public void pause(long soundId) {
        soundEffect.pause(soundId);
    }

    /**
     * Resumes a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to resume.
     */
    public void resume(long soundId) {
        soundEffect.resume(soundId);
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
    public void beginCollision(Collider c1, Collider c2) {

    }

    @Override
    public void endCollision(Collider c1, Collider c2) {

    }
}
