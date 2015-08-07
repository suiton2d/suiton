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

package com.suiton2d.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Layer is a class used to provide logical order to the update of
 * {@link GameObject}. Layers are updated in order from lowest to highest.
 * A Layer with an order of 0 will be updated before a Layer with an order of
 * 5.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Layer extends Group {

    private String name;
    private int zOrder;
    private Scene scene;

    public Layer(String name) {
        this(name, 0);
    }

    public Layer(String name, int zOrder) {
        this.name = name;
        this.zOrder = zOrder;
    }

    public String getName() {
        return name;
    }

    public int getZOrder() {
        return zOrder;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public boolean remove() {
        boolean removed = super.remove();
        if (removed)
            scene = null;
        return removed;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    /**
     * Retrieves the GameObject in the layer with the given name.
     * @param name The name of the GameObject to retrieve.
     * @return The target GameObject.
     */
    public GameObject getGameObject(String name) {
        Actor actor = findActor(name);
        return actor != null ? (GameObject) actor : null;
    }

    public void start() {
        for (Actor actor : getChildren())
            ((GameObject) actor).start();
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        if(actor instanceof GameObject)
            ((GameObject)actor).setLayer(this);
    }

    public void finish() {
        for (Actor actor : getChildren())
            ((GameObject) actor).finish();
    }

    public void beginCollision(GameObject go1, GameObject go2) {
        for (Actor actor : getChildren())
            ((GameObject) actor).beginCollision(go1, go2);
    }

    public void endCollision(GameObject go1, GameObject go2) {
        for (Actor actor : getChildren())
            ((GameObject) actor).endCollision(go1, go2);
    }
}
