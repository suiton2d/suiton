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

import com.nebula2d.editor.ui.MainFrame;

import javax.swing.tree.DefaultMutableTreeNode;

public class BaseSceneNode extends DefaultMutableTreeNode {

    protected String name;

    public BaseSceneNode(String name) {
        this.name = name;
    }

    //region accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    public void addGameObject(GameObject go) {
        add(go);
        MainFrame.getSceneGraph().refresh();
    }

    public void remove() {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) getParent();
        parent.remove(this);
        MainFrame.getSceneGraph().refresh();
    }

    @Override
    public String toString() {
        return name;
    }
}
