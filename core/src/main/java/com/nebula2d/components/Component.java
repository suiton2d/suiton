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

package com.nebula2d.components;

import com.nebula2d.scene.GameObject;

/**
 * Component is an abstract base class for nebula2d's components.
 * Components are an integral part of N2D's component-bsaed entity
 * system. components define the functionality for {@link com.nebula2d.scene.GameObject}.
 *
 * @author Jon Bonazza
 */
public abstract class Component {
    protected String name;
    protected GameObject gameObject;
    protected boolean enabled;

    public Component() {}

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Abstract callback method called exactly once at the start of the {@link com.nebula2d.scene.Scene}.
     */
    public abstract void start();

    /**
     * Abstract callback method called once per frame.
     * @param dt The time since the last frame update.
     */
    public abstract void update(float dt);

    /**
     * Abstract callback method called exactly once immediately before a {@link com.nebula2d.scene.Scene}
     * is changed or ended otherwise.
     */
    public abstract void finish();

    public abstract void beginCollision(GameObject go1, GameObject go2);

    public abstract void endCollision(GameObject go1, GameObject go2);
}
