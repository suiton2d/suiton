/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.assets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AssetManager is a singleton class used to manage a game's assets
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class AssetManager {

    private static AssetManager instance;
    private Map<String, List<Asset>> assetMap;

    private AssetManager() {
        this.assetMap = new HashMap<String, List<Asset>>();
    }

    /**
     * Gets the singleton instance of AssetManager. Only one instance of
     * AssetManager should exist at any given time
     * @return the singleton AssetManager instance
     */
    public static synchronized AssetManager getInstance() {
        if (instance == null)
            instance = new AssetManager();

        return instance;
    }

    /**
     * Loads the assets for the {@link com.nebula2d.scene.Scene} with the given name into memory.
     * @param sceneName the name of the Scene whose assets should be loaded.
     */
    public void loadAssets(String sceneName) {
        List<Asset> assets = assetMap.get(sceneName);
        for (Asset asset : assets)
            asset.load();
    }

    /**
     * Unloads the assets for the Scene with the given name from memory.
     * @param sceneName the name of the Scene whose assets should be unloaded.
     */
    public void unloadAssets(String sceneName) {
        List<Asset> assets = assetMap.get(sceneName);
        for (Asset asset : assets)
            asset.unload();
    }

    public void loadFromData() {
        //TODO: implement
    }
}
