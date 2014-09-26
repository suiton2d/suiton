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

import com.badlogic.gdx.utils.XmlWriter;
import com.nebula2d.editor.common.IBuildable;
import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

/**
 * an abstract base class for a component. components help form the
 * core of N2D's component-based entity (CBE) system. and define
 * the functionality of a game object
 */
public abstract class Component implements ISerializable, IBuildable {

    public static enum ComponentType {
        RENDER,
        MUSIC,
        SFX,
        BEHAVE,
        RIGID_BODY,
        COLLIDER
    }

    protected String name;
    protected GameObject parent;
    protected boolean enabled;
    protected ComponentType componentType;

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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

    /**
     * abstract method for creating the content JPanel used in the component dialog for this component
     * @return a panel containing the common top panel and the component specific controls
     */
    public abstract N2DPanel forgeComponentContentPanel(final ComponentsDialog parent);

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeLine(componentType.name());

        fw.writeBoolLine(enabled);
    }

    @Override
    public void build(XmlWriter sceneXml, XmlWriter assetsXml) throws IOException {
        sceneXml.element("component").
                attribute("name", name).
                attribute("enabled", enabled).
                attribute("componentType", componentType.name());
    }

    @Override
    public String toString() {
        return name;
    }
}
