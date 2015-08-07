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

package com.suiton2d.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Transform;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public abstract class CollisionShape {

    private PhysicsMaterial physicsMaterial;

    public CollisionShape(PhysicsMaterial physicsMaterial) {
        this.physicsMaterial = physicsMaterial;
    }

    public PhysicsMaterial getPhysicsMaterial() {
        return physicsMaterial;
    }

    @SuppressWarnings("unused")
    public void setPhysicsMaterial(PhysicsMaterial physicsMaterial) {
        this.physicsMaterial = physicsMaterial;
    }

    public Fixture affixTo(Body body, boolean isSensor) {
        Transform transform = new Transform((GameObject) body.getUserData());
        FixtureDef fixtureDef = createFixtureDef(transform, isSensor);
        return body.createFixture(fixtureDef);
    }

    protected abstract Shape getShape(Transform transform);

    private FixtureDef createFixtureDef(Transform transform, boolean isSensor) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = getShape(transform);
        fixtureDef.density = physicsMaterial.getDensity();
        fixtureDef.friction = physicsMaterial.getFriction();
        fixtureDef.restitution = physicsMaterial.getRestitution();
        fixtureDef.isSensor = isSensor;

        return fixtureDef;
    }
}
