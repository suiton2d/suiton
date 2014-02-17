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

    public long play() {
        return soundEffect.play();
    }

    public long loop() {
        return soundEffect.loop();
    }

    public void stop(long soundId) {
        soundEffect.stop(soundId);
    }

    public void pause(long soundId) {
        soundEffect.pause(soundId);
    }

    public void resume(long soundId) {
        soundEffect.resume(soundId);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }
}
