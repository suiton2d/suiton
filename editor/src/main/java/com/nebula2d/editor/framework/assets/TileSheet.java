package com.nebula2d.editor.framework.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.nebula2d.editor.common.IRenderable;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public abstract class TileSheet extends Asset implements IRenderable {

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
    public void dispose() {
        if (tileMap != null) {
            Gdx.app.postRunnable(() -> {
                tileMap.dispose();
                isLoaded = false;
            });
        }
    }

    public abstract Map getMap();

    public TileSheetType getType() {
        return type;
    }

    @Override
    public boolean isReady() {
        return isLoaded;
    }
}
