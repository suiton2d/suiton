package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    public abstract void render(GameObject selectedObject, SpriteBatch batcher);
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
}
