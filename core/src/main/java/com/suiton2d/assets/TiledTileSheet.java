package com.suiton2d.assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TiledTileSheet extends TileSheet<TiledMap> {
    private float unitScale = 1f;
    private int width;
    private int height;

    public TiledTileSheet(String path, TiledMap tileMap) {
        super(path, tileMap, TileSheetType.TILED);
        calculateBounds();
    }

    private void calculateBounds() {
        for (TiledMapTileSet tileSet : data.getTileSets()) {
            for (int i = 0; i < tileSet.size(); ++i) {
                TextureRegion region = tileSet.getTile(i).getTextureRegion();
                if (region.getRegionWidth() > width)
                    width = region.getRegionWidth();
                if (region.getRegionHeight() > height)
                    height = region.getRegionHeight();
            }
        }
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
