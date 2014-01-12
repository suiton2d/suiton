package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.ui.KeyFrameAnimationEditDialog;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

public class KeyFrameAnimation extends Animation {

    //region members
    protected TextureRegion[] frames;
    protected int frameWidth;
    protected int frameHeight;
    protected int startFrame;
    protected int endFrame;
    protected float speed;
    protected Texture tex;
    //endregion

    //region constructors
    /**
     * Ctor used for loading animation form disk
     * @param tex the texture to associate with the animation
     */
    public KeyFrameAnimation(String name, Texture tex) {
        super(name);
        this.tex = tex;
    }

    /**
     * Ctor used for creating a completely new animation
     * @param tex the texture to associate with the animation
     * @param frameWidth the width of a single frame
     * @param frameHeight the height of a single frame
     * @param speed the speed at which the animaiton should be played back
     */
    public KeyFrameAnimation(String name, Texture tex, int frameWidth, int frameHeight, int startFrame, int endFrame, float speed) {
        super(name);
        this.tex = tex;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.speed = speed;
        this.startFrame = startFrame;
        this.endFrame = endFrame;

        init();
    }
    //endregion

    //region accessors

    public TextureRegion[] getFrames() {
        return frames;
    }

    public int getFrameWidth() { return frameWidth; }

    public int getFrameHeight() {
        return frameHeight;
    }

    public float getSpeed() {
        return speed;
    }

    public int getStartFrameIndex() { return startFrame; }

    public int getEndFrameIndex() { return endFrame; }

    public TextureRegion getStartFrame() { return frames[startFrame]; }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public void setFrameHeight(int frameHeight) { this.frameHeight = frameHeight; };

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setStartFrameIndex(int startFrame) { this.startFrame = startFrame; }

    public void setEndFrameIndex(int endFrame) { this.endFrame = endFrame; }
    //endregion

    //region overridden methods from Animation
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        this.frameWidth = fr.readIntLine();
        this.frameHeight = fr.readIntLine();
        this.speed = fr.readFloatLine();
        this.startFrame = fr.readIntLine();
        this.endFrame = fr.readIntLine();
        init();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeIntLine(this.frameWidth);
        fw.writeIntLine(this.frameHeight);
        fw.writeFloatLine(this.speed);
        fw.writeIntLine(this.startFrame);
        fw.writeIntLine(this.endFrame);
    }

    @Override
    public void showAnimationEditDialog() {
        new KeyFrameAnimationEditDialog(this);
    }
    
    @Override
    public void renderStill(SpriteBatch batch, GameObject gameObject, Camera cam) {
        TextureRegion frame = getStartFrame();
        float halfw = tex.getWidth() / 2.0f;
        float halfh = tex.getHeight() / 2.0f;
        batch.draw(frame,
                gameObject.getPosition().x - halfw - cam.position.x,
                gameObject.getPosition().y - halfh - cam.position.y,
                gameObject.getPosition().x - halfw - cam.position.x,
                gameObject.getPosition().y - halfh - cam.position.y,
                frame.getRegionWidth(),
                frame.getRegionHeight(),
                gameObject.getScale().x,
                gameObject.getScale().y,
                gameObject.getRotation());
    }

    @Override
    public void renderAnimated(SpriteBatch batch, Camera cam) {

        if (frames != null) {
            float dt = Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = new com.badlogic.gdx.graphics.
                    g2d.Animation(speed, frames).getKeyFrame(dt);

            batch.draw(currentFrame, 0, 0, cam.viewportWidth, cam.viewportHeight);
        }
    }

    @Override
    public Texture getTexture() { return tex; }

    @Override
    public void init() {

        if (frameWidth > 0 && frameHeight > 0 && startFrame > 0 && endFrame > 0 && endFrame > startFrame) {
            com.badlogic.gdx.graphics.Texture tmpTexture = tex.getTexture();
            TextureRegion[][] tmpRegions = TextureRegion.split(tmpTexture, frameWidth, frameHeight);

            int numRows = tmpRegions.length;
            int numCols = tmpRegions[0].length;
            frames = new TextureRegion[numRows * numCols];

            //FIXME: This can be optimized, but I can't be assed with it at the moment.
            int index = 0;
            for (int i = 0; i < numRows; ++i) {
                for (int j = 0; j < numCols; ++j) {
                    frames[index++] = tmpRegions[i][j];
                }
            }

            frames = Arrays.copyOfRange(frames, startFrame, endFrame);
        }
    }
    //endregion
}
