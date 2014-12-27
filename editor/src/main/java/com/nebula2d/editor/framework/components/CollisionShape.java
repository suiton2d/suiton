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

package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlWriter;
import com.nebula2d.editor.common.IBuildable;
import com.nebula2d.editor.common.IRenderable;
import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.framework.GameObject;

import javax.swing.*;
import java.io.IOException;

/**
 *
 * Created by bonazza on 5/3/14.
 */
public abstract class CollisionShape implements ISerializable, IRenderable, IBuildable {
    public static enum ShapeType {
        BOX,
        CIRCLE
    }

    protected PhysicsMaterial material;
    protected GameObject gameObject;

    protected ShapeType shapeType;

    protected CollisionShape(GameObject gameObject) {
        this.gameObject = gameObject;
        material = new PhysicsMaterial();
    }

    public abstract void render(GameObject selectedObject, SpriteBatch batch, Camera cam);

    public PhysicsMaterial getMaterial() {
        return material;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public abstract JPanel createEditorPanel();

    @Override
    public void build(XmlWriter sceneXml, XmlWriter assetsXml, int sceneId) throws IOException {
        sceneXml.element("collisionShape").
                attribute("shapeType", shapeType.name());
        material.build(sceneXml, assetsXml);
    }
}
