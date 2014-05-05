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

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nebula2d.scene.Transform;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class BoundingBox extends CollisionShape {

    public static class Extents {
        public float halfw;
        public float halfh;

        public Extents(float halfw, float halfh) {
            this.halfw = halfw;
            this.halfh = halfh;
        }
    }

    private Extents extents;

    public BoundingBox(PhysicsMaterial physicsMaterial, float w, float h) {
        super(physicsMaterial);
        extents = new Extents(w/2.0f, h/2.0f);
        shapeType = ShapeType.BOX;
    }

    @Override
    protected Shape getShape(Transform transform) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(extents.halfw, extents.halfh, transform.getPosition(), transform.getRotation());
        return shape;
    }
}
