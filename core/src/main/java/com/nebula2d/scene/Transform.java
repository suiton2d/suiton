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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

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
    private GameObject gameObject;

    public Transform(GameObject gameObject) {
        this.gameObject = gameObject;
        this.position = new Vector2(0, 0);
        this.scale = new Vector2(1, 1);
    }

    public Vector2 getPosition() {
        float x = gameObject.getX() + gameObject.getWidth() / 2.0f;
        float y = gameObject.getY() + gameObject.getHeight() / 2.0f;
        return position.set(x, y);
    }

    public Vector2 getScale() {
        float x = gameObject.getScaleX();
        float y = gameObject.getScaleY();
        return scale.set(x, y);
    }

    public float getRotation() {
        return gameObject.getRotation();
    }

    public void setPosition(Vector2 position) {
        gameObject.addAction(Actions.moveTo(position.x, position.y));
    }

    public void setPosition(float x, float y) {
        gameObject.addAction(Actions.moveTo(x, y));
    }

    public void setScale(Vector2 scale) {
        gameObject.addAction(Actions.scaleTo(scale.x, scale.y));
    }

    public void setScale(float x, float y) {
        gameObject.addAction(Actions.scaleTo(x, y));
    }

    public void setRotation(float rotation) {
        gameObject.addAction(Actions.rotateTo(rotation));
    }

    /**
     * Translates the position of the transform.
     * @param other A Vector2 containing the delta to translate.
     */
    public void translate(Vector2 other) {
        gameObject.addAction(Actions.moveBy(other.x, other.y));
    }

    /**
     * Translates the position of the transform.
     * @param dx The amount to translate the x coordinate.
     * @param dy The amount to translate the y coordinate.
     */
    public void translate(float dx, float dy) {
        gameObject.addAction(Actions.moveBy(dx, dy));
    }

    /**
     * Rotates the transform.
     * @param theta The amount to rotate in degrees.
     */
    public void rotate(float theta) {
        gameObject.addAction(Actions.rotateBy(theta));
    }
}
