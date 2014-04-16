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

package com.nebula2d.util;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nebula2d.scene.GameObject;
import com.nebula2d.scene.Scene;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class CollisionListener implements ContactListener {

    private Scene scene;

    public CollisionListener(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void beginContact(Contact contact) {
        GameObject go1 = (GameObject) contact.getFixtureA().getUserData();
        GameObject go2 = (GameObject) contact.getFixtureB().getUserData();

        scene.beginCollision(go1, go2);
    }

    @Override
    public void endContact(Contact contact) {
        GameObject go1 = (GameObject) contact.getFixtureA().getUserData();
        GameObject go2 = (GameObject) contact.getFixtureB().getUserData();

        scene.endCollision(go1, go2);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
