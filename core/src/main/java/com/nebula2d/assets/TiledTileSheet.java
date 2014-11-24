package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.nebula2d.scene.GameObject;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TiledTileSheet extends TileSheet {

    private float unitScale = 1f;

    private int width;
    private int height;

    public TiledTileSheet(String path) {
        super(path, TileSheetType.TILED);
    }

    @Override
    public void onLoad() {

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (!loaded) {
                    tileMap = new TmxMapLoader().load(path);
                    for (MapLayer layer : tileMap.getLayers()) {
                        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;
                        int w = (int)(tiledLayer.getWidth() * tiledLayer.getTileWidth());

                        int h = (int)(tiledLayer.getHeight() * tiledLayer.getTileHeight());

                        if (w > width)
                            width = w;

                        if (h > height)
                            height = h;
                    }

                    loaded = true;
                }
            }
        });
    }

    public float getUnitScale() {
        return unitScale;
    }

    public void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    @Override
    public TiledMap getMap() {
        return (TiledMap) tileMap;
    }

    @Override
    public void render(MapRenderer renderer, GameObject gameObject, Batch batcher, Camera cam) {
        OrthographicCamera camera = (OrthographicCamera) cam;
        camera.translate((getBoundingWidth()/2), (getBoundingHeight()/2));
        camera.update();
        renderer.setView(camera);
        renderer.render();
        camera.translate(-(getBoundingWidth()/2), -(getBoundingHeight()/2));
        camera.update();
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
