package com.nebula2d.editor.framework.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

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
    public void initialize() {

        Gdx.app.postRunnable(() -> {
            if (!isLoaded) {
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

                isLoaded = true;
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
    public void load(FullBufferedReader fr) throws IOException {
        initialize();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        fw.writeLine(type.name());
    }

    @Override
    public TiledMap getMap() {
        return (TiledMap) tileMap;
    }

    @Override
    public void render(GameObject gameObject, SpriteBatch batcher, Camera cam) {
        OrthographicCamera camera = (OrthographicCamera) cam;
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(getMap());
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

    @Override
    public boolean isReady() {
        return super.isReady() && width > 0 && height > 0;
    }
}
