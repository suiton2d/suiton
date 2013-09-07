package com.nebula2d.editor.framework.assets;

import com.badlogic.gdx.files.FileHandle;
import com.nebula2d.editor.util.FullBufferedReader;

import java.io.File;
import java.io.IOException;

public class Texture extends Asset {

    protected com.badlogic.gdx.graphics.Texture texture;

    public Texture(String path) {
        super(path);
        texture = new com.badlogic.gdx.graphics.Texture(new FileHandle(new File(path)));
    }

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

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        //Noop!
    }
}
