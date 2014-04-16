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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.nebula2d.scene.GameObject;
import com.nebula2d.scene.SceneManager;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class RigidBody extends Component {

    private boolean isKinematic;
    private boolean fixedRotation;
    private boolean isBullet;
    private Body physicsBody;
    private CollisionShape collisionShape;

    public RigidBody(String name, CollisionShape collisionShape, boolean isKinematic,
                     boolean fixedRotation, boolean isBullet) {
        super(name);
        this.isKinematic = isKinematic;
        this.fixedRotation = fixedRotation;
        this.isBullet = isBullet;
        this.collisionShape = collisionShape;
    }

    @Override
    public void start() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isKinematic ? BodyDef.BodyType.KinematicBody : BodyDef.BodyType.DynamicBody;
        Vector2 pos = getGameObject().getTransform().getPosition();
        bodyDef.position.set(pos);
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.bullet = isBullet;
        physicsBody = SceneManager.getInstance().getCurrentScene().getPhysicalWorld().createBody(bodyDef);
        physicsBody.setUserData(getGameObject());
        if (collisionShape != null)
            collisionShape.affixTo(physicsBody, false).setUserData(gameObject);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void finish() {
        SceneManager.getInstance().getCurrentScene().getPhysicalWorld().destroyBody(physicsBody);
        physicsBody = null;
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }
}
