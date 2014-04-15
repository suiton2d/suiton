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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nebula2d.scene.SceneManager;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public abstract class Collider extends Component {

    private boolean isSensor;

    private Body physicalBody;
    private float density;
    private float friction;
    private float restitution;

    public Collider(String name, boolean isSensor, float density, float friction, float restitution) {
        super(name);
        this.isSensor = isSensor;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    @Override
    public void start() {
        BodyDef bodyDef = createBodyDef();
        FixtureDef fixtureDef = createFixtureDef();
        physicalBody = SceneManager.getInstance().getCurrentScene().getPhysicalWorld().createBody(bodyDef);
        Fixture fixture = physicalBody.createFixture(fixtureDef);
        fixture.setUserData(this);
        physicalBody.setUserData(getGameObject());
    }

    protected abstract Shape getShape();

    private BodyDef createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Vector2 pos = getGameObject().getTransform().getPosition();
        bodyDef.position.set(pos);
        bodyDef.angle = (float) (getGameObject().getTransform().getRotation() * Math.PI / 180.0f);

        return bodyDef;
    }

    private FixtureDef createFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = getShape();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = isSensor;

        return fixtureDef;
    }

    @Override
    public void update(float dt) {
        physicalBody.getPosition().set(gameObject.getTransform().getPosition());
    }

    @Override
    public void finish() {
        SceneManager.getInstance().getCurrentScene().getPhysicalWorld().destroyBody(physicalBody);
    }

    @Override
    public void beginCollision(Collider c1, Collider c2) {

    }

    @Override
    public void endCollision(Collider c1, Collider c2) {

    }
}
