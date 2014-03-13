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

package com.nebula2d.scene;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Layer is a class used to provide logical order to the update of
 * {@link GameObject}. Layers are updated in order from lowest to highest.
 * A Layer with an order of 0 will be updated before a Layer with an order of
 * 5.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Layer {

    private String name;
    private int zOrder;
    private List<GameObject> gameObjects;
    private Scene scene;

    public Layer(String name, int zOrder) {
        this.name = name;
        this.zOrder = zOrder;
        gameObjects = new ArrayList<GameObject>();
    }

    //region accessors
    public String getName() {
        return name;
    }

    public int getZOrder() {
        return zOrder;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    //endregion

    //region game object ops

    /**
     * Retrieves the GameObject in the layer with the given name.
     * @param name The name of the GameObject to retrieve.
     * @return The target GameObject.
     */
    public GameObject getGameObject(String name) {
        for (GameObject go : gameObjects) {
            GameObject hit = (GameObject) go.findActor(name);
            if (hit != null)
                return hit;
        }

        return null;
    }

    /**
     * Adds a GameObject to the Layer.
     * @param gameObject The GameObject to add.
     */
    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.setLayer(this);
    }

    /**
     * Removes a GameObject from the Layer.
     * @param gameObject The GameObject to remove.
     */
    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObject.setLayer(null);
    }
    //endregion

    public void start(Stage stage) {
        for (GameObject go : gameObjects)
            go.start(stage);
    }

    public void update(float dt) {
        for (GameObject go : gameObjects)
            go.update(dt);
    }
}
