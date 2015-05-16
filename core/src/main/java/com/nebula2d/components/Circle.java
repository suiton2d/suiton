/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
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

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.gson.annotations.Expose;
import com.nebula2d.scene.Transform;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Circle extends CollisionShape {

    @Expose
    private float radius;

    public Circle() {}

    public Circle(PhysicsMaterial physicsMaterial, float radius) {
        super(physicsMaterial);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    protected Shape getShape(Transform transform) {
        CircleShape circleShape = new CircleShape();
        circleShape.setPosition(transform.getPosition());
        circleShape.setRadius(radius);

        return circleShape;
    }
}
