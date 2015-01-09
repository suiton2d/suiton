package com.nebula2d.assets;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TiledTileSheet extends TileSheet<TiledMap> {

    private float unitScale = 1f;

    private int width;
    private int height;

    public TiledTileSheet(String path, TiledMap tileMap) {
        super(path, tileMap, TileSheetType.TILED);
    }

    public float getUnitScale() {
        return unitScale;
    }

    public void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    @Override
    public int getBoundingWidth() {
        return width;
    }

    @Override
    public int getBoundingHeight() {
        return height;
    }
}
