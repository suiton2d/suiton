package com.nebula2d.assets;

import com.badlogic.gdx.maps.Map;

public abstract class TileSheet<T extends Map> extends Asset<T> {

    public static enum TileSheetType {
        TILED,
    }

    protected TileSheetType type;

    public TileSheet(String path, T tileMap, TileSheetType type) {
        super(path, tileMap);
        this.type = type;
    }

    public TileSheetType getType() {
        return type;
    }

    public abstract int getBoundingWidth();

    public abstract int getBoundingHeight();
}
