package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import java.io.IOException;

public abstract class Animation implements ISaveable, ILoadable{

    //region members
    protected String name;
    //endregion

    //region constructor
    public Animation(String name) {
        this.name = name;
    }
    //endregion

    //region Accessors
    public String getName() {
        return name;
    }

    public abstract Texture getTexture();

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    //region interface overrides
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
    }
    //endregion

    @Override
    public String toString() {
        return name;
    }

    public abstract void showAnimationEditDialog();

    public abstract void renderStill(SpriteBatch batch, GameObject gameObject, Camera cam);

    public abstract void renderAnimated(SpriteBatch batch, Camera cam, int canvasW, int canvasH);

    public abstract void init();

}
