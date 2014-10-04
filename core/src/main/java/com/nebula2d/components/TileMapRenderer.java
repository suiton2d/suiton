package com.nebula2d.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.nebula2d.assets.TileSheet;
import com.nebula2d.scene.SceneManager;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TileMapRenderer extends Renderer {

    private TileSheet tileSheet;

    public TileMapRenderer(String name, TileSheet tileSheet) {
        super(name);
        this.tileSheet = tileSheet;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Batch batch, float dt) {
        if (tileSheet != null && tileSheet.isLoaded())
            tileSheet.render(gameObject, batch, SceneManager.getInstance().getCurrentScene().getCamera());
    }

    public int getBoundingWidth() {
        return tileSheet != null ? tileSheet.getBoundingWidth() : 0;
    }

    public int getBoundingHeight() {
        return tileSheet != null ? tileSheet.getBoundingHeight() : 0;
    }
}
