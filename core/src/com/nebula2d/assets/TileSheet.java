package com.nebula2d.assets;

/**
 * Created by bonazza on 2/22/14.
 */
public class TileSheet extends Sprite {

    protected int tileWidth;
    protected int tileHeight;

    public TileSheet(String path, int tileWidth, int tileHeight) {
        super(path);

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
