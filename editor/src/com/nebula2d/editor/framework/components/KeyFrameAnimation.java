package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

public class KeyFrameAnimation extends Animation {

    //region members
    protected TextureRegion[] frames;
    protected int numRows;
    protected int numCols;
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
     * @param numRows the number of rows in the sprite sheet
     * @param numCols the number of cols in the sprite sheet
     * @param speed the speed at which the animaiton should be played back
     */
    public KeyFrameAnimation(String name, Texture tex, int numRows, int numCols, int startFrame, int endFrame, float speed) {
        super(name);
        this.tex = tex;
        this.numRows = numRows;
        this.numCols = numCols;
        this.speed = speed;
        this.startFrame = startFrame;
        this.endFrame = endFrame;

        init();
    }
    //endregion

    //region internal methods
    protected void init() {
        com.badlogic.gdx.graphics.Texture tmpTexture = tex.getTexture();
        TextureRegion[][] tmpRegions = TextureRegion.split(tmpTexture, tmpTexture.getWidth() / numCols, tmpTexture.getHeight() / numRows);
        TextureRegion[] frames = new TextureRegion[numRows * numCols];

        int index = 0;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                frames[index++] = tmpRegions[i][j];
            }
        }

    }
    //endregion

    //region accessors
    public TextureRegion[] getFrames() {
        return frames;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public float getSpeed() {
        return speed;
    }

    public void setNumRows(int rows) {
        numRows = rows;
    }

    public void setNumCols(int cols) {
        numCols = cols;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    //endregion

    //region overrided methods from Animation
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        this.numRows = fr.readIntLine();
        this.numCols = fr.readIntLine();
        this.speed = fr.readFloatLine();
        this.startFrame = fr.readIntLine();
        this.endFrame = fr.readIntLine();
        init();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeIntLine(this.numRows);
        fw.writeIntLine(this.numCols);
        fw.writeFloatLine(this.speed);
        fw.writeIntLine(this.startFrame);
        fw.writeIntLine(this.endFrame);
    }
    //endregion
}
