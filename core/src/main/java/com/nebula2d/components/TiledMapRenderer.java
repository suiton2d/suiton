package com.nebula2d.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.nebula2d.assets.AssetManager;
import com.nebula2d.assets.TiledTileSheet;
import com.nebula2d.scene.GameObject;
import com.nebula2d.scene.SceneManager;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TiledMapRenderer extends Component implements Renderer {

    private String filename;
    private OrthogonalTiledMapRenderer renderer;

    public TiledMapRenderer(String name, String filename) {
        super(name);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public TiledTileSheet getTiledTileSheet() {
        return AssetManager.getAsset(filename, TiledTileSheet.class);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Batch batch, float dt) {
        if (getTiledTileSheet() != null) {
            if (renderer == null)
                renderer = new OrthogonalTiledMapRenderer(getTiledTileSheet().getData());
            OrthographicCamera camera = (OrthographicCamera) SceneManager.getCurrentScene().getCamera();
            camera.translate((getBoundingWidth()/2), (getBoundingHeight()/2));
            camera.update();
            renderer.setView(camera);
            renderer.render();
            camera.translate(-(getBoundingWidth()/2), -(getBoundingHeight()/2));
            camera.update();
        }
    }

    @Override
    public int getBoundingWidth() {
        return getTiledTileSheet() != null ? getTiledTileSheet().getBoundingWidth() : 0;
    }

    @Override
    public int getBoundingHeight() {
        return getTiledTileSheet() != null ? getTiledTileSheet().getBoundingHeight() : 0;
    }

    @Override
    public void finish() {
        if (renderer != null)
            renderer.dispose();
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }
}
