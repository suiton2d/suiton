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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.SceneManager;
import com.suiton2d.scene.Transform;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Collider<T extends CollisionShape> extends Component {

    protected boolean isSensor;
    protected T collisionShape;
    protected Body physicalBody;

    public Collider() {}

    public Collider(String name, T collisionShape, boolean isSensor) {
        super(name);
        this.isSensor = isSensor;
        this.collisionShape = collisionShape;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void setIsSensor(boolean isSensor) {
        this.isSensor = isSensor;
    }

    public T getCollisionShape() {
        return collisionShape;
    }

    public void setCollisionShape(T collisionShape) {
        this.collisionShape = collisionShape;
    }

    @Override
    public void start() {
        BodyDef bodyDef = createBodyDef();
        physicalBody = SceneManager.getCurrentScene().getPhysicalWorld().createBody(bodyDef);
        physicalBody.setUserData(getGameObject());
        collisionShape.affixTo(physicalBody, isSensor).setUserData(gameObject);
    }

    private BodyDef createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Transform transform = new Transform(getGameObject());
        Vector2 pos = transform.getPosition();
        bodyDef.position.set(pos);
        bodyDef.angle = (float) (transform.getRotation() * Math.PI / 180.0f);

        return bodyDef;
    }

    @Override
    public void update(float dt) {
        physicalBody.getPosition().set(gameObject.getX(), gameObject.getY());
    }

    @Override
    public void finish() {
        SceneManager.getCurrentScene().getPhysicalWorld().destroyBody(physicalBody);
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }
}
