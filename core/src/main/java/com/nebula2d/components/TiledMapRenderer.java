package com.nebula2d.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.nebula2d.assets.TiledTileSheet;
import com.nebula2d.scene.SceneManager;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TiledMapRenderer extends Renderer {

    private TiledTileSheet tileSheet;
    private OrthogonalTiledMapRenderer renderer;

    public TiledMapRenderer(String name, TiledTileSheet tileSheet) {
        super(name);
        this.tileSheet = tileSheet;
        renderer = new OrthogonalTiledMapRenderer(tileSheet.getMap());
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
            tileSheet.render(renderer, gameObject, batch, SceneManager.getInstance().getCurrentScene().getCamera());
    }

    @Override
    public void finish() {
        if (renderer != null)
            renderer.dispose();
    }

    public int getBoundingWidth() {
        return tileSheet != null ? tileSheet.getBoundingWidth() : 0;
    }

    public int getBoundingHeight() {
        return tileSheet != null ? tileSheet.getBoundingHeight() : 0;
    }
}
