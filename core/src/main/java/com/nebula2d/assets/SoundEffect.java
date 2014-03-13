package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * SoundEffect is an {@link com.nebula2d.assets.Asset} for the
 * playback of sound effects. SoundEffects are sampled.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SoundEffect extends Asset {

    private Sound sound;

    public SoundEffect(String path) {
        super(path);
    }

    /**
     * Plays the SoundEffect.
     * @return The sound ID associated with the current playback instance.
     */
    public long play() {
        return sound.play();
    }

    /**
     * Plays the SoundEffect at a given volume.
     * @param volume The volume at which to play the SoundEffect.
     * @return The sound ID associated with th current playback instance.
     */
    public long play(float volume) {
        return sound.play(volume);
    }

    /**
     * Plays the SoundEffect in a loop.
     * @return The sound ID associated with th current playback instance.
     */
    public long loop() {
        return sound.loop();
    }

    /**
     * Plays the SoundEffect in a loop at the given volume.
     * @param volume The volume at which to play the SoundEffect.
     * @return The sound ID associated with th current playback instance.
     */
    public long loop(float volume) {
        return sound.loop(volume);
    }

    /**
     * Stops playback of a given SoundEffect playback instance.
     * @param soundId The sound ID of the playback instance to stop.
     */
    public void stop(long soundId) {
        sound.stop(soundId);
    }

    /**
     * Pauses playback of a given SoundEffect playback instance.
     * @param soundId The sound ID of the playback instance to pause.
     */
    public void pause(long soundId) {
        sound.pause(soundId);
    }

    /**
     * Resumes playback of a given SoundEffect playback instance.
     *
     * @param soundId The sound ID of the playback instance to resume.
     */
    public void resume(long soundId) {
        sound.resume(soundId);
    }

    @Override
    protected void onLoad() {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    @Override
    protected void onUnload() {
        sound.dispose();
        sound = null;
    }

    public Sound getSound() {
        return sound;
    }
}
