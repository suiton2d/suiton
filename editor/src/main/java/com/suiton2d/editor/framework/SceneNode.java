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

package com.suiton2d.editor.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.suiton2d.editor.ui.scene.SceneGraph;

import javax.swing.tree.DefaultMutableTreeNode;

public class SceneNode<T> extends DefaultMutableTreeNode {

    private SceneGraph sceneGraph;
    private String name;
    private T data;

    public SceneNode(SceneGraph sceneGraph, String name) {
        this.sceneGraph = sceneGraph;
        this.name = name;
    }

    public SceneNode(SceneGraph sceneGraph, String name, T data) {
        this(sceneGraph, name);
        this.data = data;
    }

    public <E> void add(String name, E data) {
        SceneNode sceneNode = new SceneNode<>(sceneGraph, name, data);
        super.add(sceneNode);
        if (sceneNode.getData() != null && sceneNode.getData() instanceof Actor && getData() instanceof Group)
            ((Group)getData()).addActor((Actor)sceneNode.getData());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void remove() {
        SceneNode parent = (SceneNode) getParent();
        if (data instanceof Actor)
            ((Actor)data).remove();
        parent.remove(this);
        sceneGraph.refresh();
    }

    @Override
    public String toString() {
        return name;
    }
}
