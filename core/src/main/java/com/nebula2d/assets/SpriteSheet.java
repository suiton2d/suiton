package com.nebula2d.assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

/**
 * SpriteSheet is a {@link Sprite} used to render KeyFrameAnimations.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SpriteSheet extends Sprite {

    private TextureRegion[] frames;
    private int frameWidth;
    private int frameHeight;


    public SpriteSheet(String path, int frameWidth, int frameHeight) {
        super(path);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        init();
    }

    private void init() {
        if (frameWidth > 0 && frameHeight > 0) {
            TextureRegion[][] tmpRegions = TextureRegion.split(getTexture(), frameWidth, frameHeight);
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
        }
    }

    public TextureRegion getFrame(int idx) {
        return frames[idx];
    }

    public TextureRegion[] getFrames(int startFrame, int endFrame) {
        return Arrays.copyOfRange(frames, startFrame, endFrame + 1);
    }
}
