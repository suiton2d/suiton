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

    public long play() {
        return sound.play();
    }

    public long play(float volume) {
        return sound.play(volume);
    }

    public long loop() {
        return sound.loop();
    }

    public long loop(float volume) {
        return sound.loop(volume);
    }

    public void stop(long soundId) {
        sound.stop(soundId);
    }

    public void pause(long soundId) {
        sound.pause(soundId);
    }

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
