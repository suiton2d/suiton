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

import com.badlogic.gdx.math.Vector2;

/**
 * Transform is a class representing for position, scale, and rotation
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Transform {

    /*
    We use position and scale buffers to prevent
    having to instantiate a new Vector2 every operation
     */
    private Vector2 position;
    private Vector2 scale;
    private float rotation;

    public Transform(GameObject gameObject) {
        setPosition(gameObject.getX(), gameObject.getY());
        setScale(gameObject.getScaleX(), gameObject.getScaleY());
        setRotation(gameObject.getRotation());
    }

    public Transform() {
        setPosition(0, 0);
        setScale(1.0f, 1.0f);
        setRotation(0);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position = new Vector2(x, y);
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y) {
        this.scale = new Vector2(x, y);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Translates the position of the transform.
     * @param other A Vector2 containing the delta to translate.
     */
    public void translate(Vector2 other) {
        position.add(other);
    }

    /**
     * Translates the position of the transform.
     * @param dx The amount to translate the x coordinate.
     * @param dy The amount to translate the y coordinate.
     */
    public void translate(float dx, float dy) {
        position.add(dx, dy);
    }

    /**
     * Rotates the transform.
     * @param theta The amount to rotate in degrees.
     */
    public void rotate(float theta) {
        rotation += theta;

        if (rotation >= 360) {
            rotation -= 360;
        }
    }
}
