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

package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import java.io.IOException;

/**
 * an abstract base class for a component. components help form the
 * core of N2D's component-based entity (CBE) system. and define
 * the functionality of a game object
 */
public abstract class Component implements ISaveable, ILoadable {

    //region constants
    public static final int COMPONENT_TYPE_RENDER = 0;
    public static final int COMPONENT_TYPE_AUDIO = 1;
    public static final int COMPONENT_TYPE_BEHAVE = 2;
    public static final int COMPONENT_TYPE_RIGID_BODY = 3;
    //endregion

    //region members
    protected String name;
    protected GameObject parent;
    protected boolean enabled;
    //endregion

    //region constructor
    public Component(String name) {
        this.name = name;
    }
    //endregion

    //region Accessors
    public String getName() {
        return this.name;
    }

    public GameObject getParent() {
        return this.parent;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //endregion

    //region public methods
    /**
     * abstract method for rendering a component in the scene
     * @param selectedObject the selected object in the render canvas
     * @parem batcher the sprite batcher used for rendering
     */
    public abstract void render(GameObject selectedObject, SpriteBatch batcher, Camera cam);

    /**
     * abstract method for creating the content JPanel used in the component dialog for this component
     * @return a panel containing the common top panel and the component specific controls
     */
    public abstract JPanel forgeComponentContentPanel(final ComponentsDialog parent);
    //endregion

    //region interface overrides
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeBoolLine(enabled);
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        enabled = fr.readBooleanLine();
    }
    //endregion

    @Override
    public String toString() {
        return name;
    }
}
