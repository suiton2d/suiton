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

package com.nebula2d.editor.framework;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.framework.components.*;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class GameObject extends BaseSceneNode implements ISerializable {

    protected Vector2 pos;
    protected  Vector2 scale;
    protected float rot;

    protected List<Component> components;
    protected Renderer renderer;

    public GameObject(String name) {
        super(name);
        components = new ArrayList<Component>();
        pos = new Vector2();
        scale = new Vector2(1, 1);
        rot = 0;
    }

    //region accessors
    public Vector2 getPosition() {
        return pos;
    }

    public Vector2 getScale() {
        return scale;
    }

    public float getRotation() {
        return rot;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setPosition(float x, float y) {
        float dx = x - pos.x;
        float dy = y - pos.y;
        translate(dx, dy);
    }

    public void translate(float dx, float dy) {
        pos.x += dx;
        pos.y += dy;

        Enumeration children = children();
        while(children.hasMoreElements()) {
            GameObject go = (GameObject) children.nextElement();
            go.translate(dx, dy);
        }
    }

    public void setScale(float x, float y) {
        scale.x = x;
        scale.y = y;
    }

    public void setRotation(float rot) {
        this.rot = rot;
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public void addComponent(Component comp) {
        components.add(comp);
        comp.setParent(this);

        if (comp instanceof Renderer) {
            renderer = (Renderer) comp;
        }
    }

    public void removeComponent(Component comp) {
        components.remove(comp);
        if (renderer == comp)
            renderer = null;
    }

    public Component getComponent(String name) {
        for (Component c : components) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }
    //endregion

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        pos.x = fr.readIntLine();
        pos.y = fr.readIntLine();
        scale.x = fr.readFloatLine();
        scale.y = fr.readFloatLine();
        rot = fr.readFloatLine();
        int size = fr.readIntLine();

        for (int i = 0; i < size; ++i) {
            String name = fr.readLine();
            Component.ComponentType type = Component.ComponentType.valueOf(fr.readLine());
            Component component = null;
            if (type == Component.ComponentType.RENDER) {
                Renderer.RendererType rendererType = Renderer.RendererType.valueOf(fr.readLine());
                if (rendererType == Renderer.RendererType.SPRITE_RENDERER)
                    component = new SpriteRenderer(name);

            } else if (type == Component.ComponentType.MUSIC) {
                component = new MusicSource(name);
            } else if (type == Component.ComponentType.SFX) {
                component = new SoundEffectSource(name);
            } else if (type == Component.ComponentType.BEHAVE) {
                component = new Behaviour(name);
            }

            if (component == null)
                throw new IOException("Failed to load project.");

            component.load(fr);
            addComponent(component);
        }

        int childCount = fr.readIntLine();

        for (int i = 0; i < childCount; ++i) {
            GameObject go = new GameObject("tmp");
            go.load(fr);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeFloatLine(pos.x);
        fw.writeFloatLine(pos.y);
        fw.writeFloatLine(scale.x);
        fw.writeFloatLine(scale.y);
        fw.writeFloatLine(rot);

        fw.writeIntLine(components.size());
        for (Component c : components) {
            c.save(fw);
        }

        fw.writeIntLine(getChildCount());
        Enumeration children = children();
        while (children.hasMoreElements()) {
            GameObject go = (GameObject) children.nextElement();
            go.save(fw);
        }
    }

    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        if (renderer != null && renderer.isEnabled())
            renderer.render(selectedObject, batcher, cam);

        Enumeration children = children();
        while (children.hasMoreElements()) {
            GameObject child = (GameObject) children.nextElement();
            child.render(selectedObject, batcher, cam);
        }
    }
}
