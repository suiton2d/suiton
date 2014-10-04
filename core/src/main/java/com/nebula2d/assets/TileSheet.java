package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.Map;
import com.nebula2d.scene.GameObject;

public abstract class TileSheet extends Asset {

    public static enum TileSheetType {
        TILED,
    }

    protected Map tileMap;

    protected TileSheetType type;

    public TileSheet(String path, TileSheetType type) {
        super(path);
        this.type = type;
    }

    @Override
    protected void onLoad() {

    }

    public abstract Map getMap();

    public TileSheetType getType() {
        return type;
    }

    @Override
    protected void onUnload() {
        if (tileMap != null) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    tileMap.dispose();
                    loaded = false;
                }
            });
        }
    }

    public abstract void render(GameObject gameObject, Batch batcher, Camera cam);

    public abstract int getBoundingWidth();

    public abstract int getBoundingHeight();
}
