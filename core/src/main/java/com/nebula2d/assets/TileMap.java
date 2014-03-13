package com.nebula2d.assets;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class TileMap extends Asset {

    protected List<TileLayer> layers;
    protected TileSheet tileSheet;


    public TileMap(String path, TileSheet tileSheet) {
        super(path);
        this.tileSheet = tileSheet;
        layers = new ArrayList<TileLayer>();
    }

    public List<TileLayer> getLayers() {
        return layers;
    }

    public TileLayer getLayer(int zOrder) {
        return layers.get(zOrder);
    }

    public void addLayer(TileLayer tileLayer) {
        layers.add(tileLayer.getZOrder(), tileLayer);
    }

    public void removeLayer(TileLayer tileLayer) {
        layers.remove(tileLayer);
    }

    public void removeLayer(int z) {
        layers.remove(z);
    }

    @Override
    protected void onLoad() {
        //Don't need to do anything special here
    }

    @Override
    protected void onUnload() {
        //Don't need to do anything special here
    }

    public class TileLayer {
        protected List<Tile> tiles;
        protected int zOrder;
        protected int numCols;
        protected int numRows;

        public TileLayer(int zOrder, int numCols, int numRows) {
            this.zOrder = zOrder;
            this.numCols = numCols;
            this.numRows = numRows;

            tiles = new ArrayList<Tile>();
        }

        public List<Tile> getTiles() {
            return tiles;
        }

        public Tile getTile(int x, int y) {
            int idx = numRows * y + x;
            return tiles.get(idx);
        }

        public void addTile(Tile tile) {
            int idx = numRows * tile.y + tile.x;
            tiles.add(idx, tile);
        }

        public void removeTile(Tile tile) {
            tiles.remove(tile);
        }

        public void removeTile(int x, int y) {
            int idx = numRows * y + x;
            tiles.remove(idx);
        }

        public int getTileCount() {
            return tiles.size();
        }

        public int getZOrder() {
            return zOrder;
        }

    }

    /**
     * A Tile is a single cell in a TileMap.
     */
    public class Tile {
        public int x;
        public int y;
        public int tileX;
        public int tileY;
    }
}
