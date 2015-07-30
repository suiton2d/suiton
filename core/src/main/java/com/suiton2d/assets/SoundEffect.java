package com.suiton2d.assets;

import com.badlogic.gdx.audio.Sound;

/**
 * SoundEffect is an {@link Asset} for the
 * playback of sound effects. SoundEffects are sampled.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SoundEffect extends Asset<Sound> {

    public SoundEffect(String path, Sound sound) {
        super(path, sound);
    }

    /**
     * Plays the SoundEffect.
     * @return The sound ID associated with the current playback instance.
     */
    public long play() {
        return data.play();
    }

    /**
     * Plays the SoundEffect at a given volume.
     * @param volume The volume at which to play the SoundEffect.
     * @return The sound ID associated with th current playback instance.
     */
    @SuppressWarnings("unused")
    public long play(float volume) {
        return data.play(volume);
    }

    /**
     * Plays the SoundEffect in a loop.
     * @return The sound ID associated with th current playback instance.
     */
    public long loop() {
        return data.loop();
    }

    /**
     * Plays the SoundEffect in a loop at the given volume.
     * @param volume The volume at which to play the SoundEffect.
     * @return The sound ID associated with th current playback instance.
     */
    @SuppressWarnings("unused")
    public long loop(float volume) {
        return data.loop(volume);
    }

    /**
     * Stops playback of a given SoundEffect playback instance.
     * @param soundId The sound ID of the playback instance to stop.
     */
    public void stop(long soundId) {
        data.stop(soundId);
    }

    /**
     * Pauses playback of a given SoundEffect playback instance.
     * @param soundId The sound ID of the playback instance to pause.
     */
    public void pause(long soundId) {
        data.pause(soundId);
    }

    /**
     * Resumes playback of a given SoundEffect playback instance.
     *
     * @param soundId The sound ID of the playback instance to resume.
     */
    public void resume(long soundId) {
        data.resume(soundId);
    }
}
