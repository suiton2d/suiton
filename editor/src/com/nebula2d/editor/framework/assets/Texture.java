package com.nebula2d.editor.framework.assets;

import com.nebula2d.editor.util.FullBufferedReader;

import java.io.IOException;

public class Texture extends Asset {

    //region members
    protected com.badlogic.gdx.graphics.Texture texture;
    //endregion

    //region constructor
    public Texture(String path) {
        super(path);
        texture = new com.badlogic.gdx.graphics.Texture(path);
    }
    //endregion

    //region Accessors
    public int getWidth() {
        return this.texture.getWidth();
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    public void bind() {
        this.texture.bind();
    }

    public com.badlogic.gdx.graphics.Texture getTexture() {
        return texture;
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        //Noop!
    }
    //endregion
}
