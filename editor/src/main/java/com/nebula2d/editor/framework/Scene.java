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
import com.nebula2d.editor.common.IRenderable;
import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.Enumeration;

public class Scene extends BaseSceneNode implements ISerializable {

    private static int sceeneCounter = 0;

    protected int id;

    public Scene(String name) {
        super(name);
        id = sceeneCounter++;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Layer getLayer(String name) {
        Enumeration children = children();
        while (children.hasMoreElements()) {
            Layer layer = (Layer) children.nextElement();
            if (layer.getName().equals(name))
                return layer;
        }

        return null;
    }

    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        Enumeration layers = children();

        while (layers.hasMoreElements()) {
            ((Layer) layers.nextElement()).render(selectedObject, batcher, cam);
        }
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        int size = fr.readIntLine();

        for (int i = 0; i < size; ++i) {
            String name = fr.readLine();
            Layer layer = new Layer(name);
            layer.load(fr);
            add(layer);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeIntLine(getChildCount());
        Enumeration layers = children();
        while (layers.hasMoreElements())
            ((Layer) layers.nextElement()).save(fw);
    }
}
