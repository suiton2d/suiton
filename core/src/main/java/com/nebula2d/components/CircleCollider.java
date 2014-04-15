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

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class CircleCollider extends Collider {

    private float radius;

    public CircleCollider(String name, boolean isSensor, float density, float friction,
                          float restitution, float radius) {
        super(name, isSensor, density, friction, restitution);
        this.radius = radius;
    }

    @Override
    protected Shape getShape() {
        CircleShape shape = new CircleShape();
        shape.setPosition(getGameObject().getTransform().getPosition());
        shape.setRadius(radius);

        return shape;
    }
}
