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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nebula2d.components.Component;
import com.nebula2d.components.Renderer;
import com.nebula2d.components.RigidBody;

import java.util.ArrayList;
import java.util.List;

/**
 * GameObject is the core of Nebula2D's component-based entity system.
 * It has a transform and a list components which describe the entity.
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class GameObject extends Group {
    protected Transform transform;
    protected List<Component> components;
    protected Layer layer;
    protected Renderer renderer;
    protected RigidBody rigidBody;

    //region constructors
    public GameObject(String name) {
        setName(name);
        this.transform = new Transform(this);
        this.components = new ArrayList<Component>();
    }
    //endregion

    //region accessors
    public Transform getTransform() {
       return transform;
    }

    public List<Component> getComponents() {
        return components;
    }

    public Layer getLayer() {
        return layer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
        for (Actor actor : getChildren())
            ((GameObject) actor).setLayer(layer);
    }
    //endregion

    //region component ops

    /**
     * Retrieves the {@link Component} from the GameObject with the given name.
     * @param name The name of the Component to retrieve.
     * @return The target Component.
     */
    public Component getComponent(String name) {
        for (Component c : components) {
            if (c.getName().equals(name))
                return c;
        }

        return null;
    }

    /**
     * Adds a Component to the GameObject.
     * @param component The Component to add.
     */
    public void addComponent(Component component) {
        components.add(component);
        component.setGameObject(this);
        if (component instanceof Renderer)
            renderer = (Renderer) component;
        else if (component instanceof RigidBody)
            rigidBody = (RigidBody) component;
    }

    /**
     * Removes a Component from the GameObject.
     * @param component The Component to remove.
     */
    public void removeComponent(Component component) {
        components.remove(component);
        component.setGameObject(null);
        if (component == renderer)
            renderer = null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (renderer != null)
            renderer.render(batch, Gdx.graphics.getDeltaTime());

        drawChildren(batch, parentAlpha);
    }

    public void start(Stage stage) {
        this.transform = new Transform(this);
        stage.addActor(this);
        for (Component c : components) {
            if (c.isEnabled())
                c.start();
        }
    }

    @Override
    public void act(float dt) {

        for (Component c : components) {
            if (!(c instanceof Renderer) && c.isEnabled())
                c.update(dt);
        }

        super.act(dt);
    }

    public void finish() {
        for (Component c : components) {
            if (c.isEnabled())
                c.finish();
        }
    }

    public void beginCollision(GameObject go1, GameObject go2) {
        for (Component c : components) {
            if (c.isEnabled())
                c.beginCollision(go1, go2);
        }
    }

    public void endCollision(GameObject go1, GameObject go2) {
        for (Component c : components) {
            if (c.isEnabled())
                c.endCollision(go1, go2);
        }
    }
    //endregion
}
