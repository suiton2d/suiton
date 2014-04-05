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
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.Enumeration;

public class Layer extends BaseSceneNode implements ISaveable, ILoadable {

    public Layer(String name) {
        super(name);
    }

    public GameObject getGameObject(String name) {

        Enumeration children = depthFirstEnumeration();

        while (children.hasMoreElements()) {
            GameObject go = (GameObject) children.nextElement();
            if (go.getName().equals(name)) {
                return go;
            }
        }

        return null;
    }

    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {

        Enumeration children = depthFirstEnumeration();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();

            if (node instanceof GameObject) {
                ((GameObject) node).render(selectedObject, batcher, cam);
            }
        }
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        name = fr.readLine();
        int size = fr.readIntLine();
        for (int i = 0; i < size; ++i) {
            GameObject go = new GameObject("tmp");
            go.load(fr);
            add(go);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeIntLine(getChildCount());
        Enumeration children = children();
        while (children.hasMoreElements()) {
            GameObject go = (GameObject) children.nextElement();
            go.save(fw);
        }
    }
}
