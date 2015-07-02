package com.suiton2d.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.suiton2d.assets.SpriteSheet;
import com.suiton2d.scene.Transform;

/**
 * KeyFrameAnimation is an {@link Animation} implementation for rendering a {@link SpriteSheet}.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class KeyFrameAnimation extends Animation {

    protected SpriteSheet spriteSheet;
    protected int startFrame;
    protected int endFrame;
    protected float speed;
    protected com.badlogic.gdx.graphics.g2d.Animation animation;
    protected float startTime;
    protected boolean wrap;
    protected TextureRegion currentFrame;

    /**
     * Constructor used for loading animation form disk
     * @param name a name for the animation
     * @param spriteSheet the {@link SpriteSheet} used for the animation
     */
    public KeyFrameAnimation(String name, SpriteSheet spriteSheet) {
        this(name, spriteSheet, 0, 0, 0.0f);
        wrap = false;

        init();
    }

    /**
     * Constructor used for creating a completely new animation
     * @param name a name for the animation
     * @param spriteSheet the {@link SpriteSheet} used for the animation
     * @param startFrame the frame in the sprite sheet to start the animation
     * @param endFrame  the frame in the sprite sheet to end the animation
     * @param speed the speed at which the animaiton should be played back
     */
    public KeyFrameAnimation(String name, SpriteSheet spriteSheet, int startFrame, int endFrame, float speed) {
        super(name);
        this.speed = speed;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        wrap = false;
        this.spriteSheet = spriteSheet;

        init();
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public int getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public int getEndFrame() {
        return endFrame;
    }

    public void setEndFrame(int endFrame) {
        this.endFrame = endFrame;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isWrap() {
        return wrap;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    private void init() {
        TextureRegion[] frames = spriteSheet.getFrames(startFrame, endFrame);
        animation = new com.badlogic.gdx.graphics.g2d.Animation(speed, frames);
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    /**
     * Updates the animation
     * @param dt the time that has elapsed since the last frame
     * @return the TextureRegion for the current frame in the animation
     */
    @Override
    protected TextureRegion update(float dt) {

        if (!isPlaying) {
            currentFrame = spriteSheet.getFrame(startFrame);
        } else if (!isPaused) {
            startTime += dt;
            currentFrame = animation.getKeyFrame(startTime, wrap);
        }

        return currentFrame;
    }

    @Override
    public void render(Transform transform, Batch batch, Camera cam, float dt) {

        TextureRegion currentFrame = update(dt);
        float halfw = currentFrame.getRegionWidth() / 2.0f;
        float halfh = currentFrame.getRegionHeight() / 2.0f;

        batch.draw(currentFrame,
                transform.getPosition().x - halfw - cam.position.x,
                transform.getPosition().y - halfh - cam.position.y,
                transform.getPosition().x - halfw - cam.position.x,
                transform.getPosition().y - halfh - cam.position.y,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                transform.getScale().x,
                transform.getScale().y,
                transform.getRotation());
    }
}
