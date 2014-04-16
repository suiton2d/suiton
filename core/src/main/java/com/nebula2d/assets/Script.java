package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.common.io.BaseEncoding;


/**
 * Script is an {@link com.nebula2d.assets.Asset} representing
 * a javascript file. Scripts are used by Behavior components to
 * provide functionality to GameObjects.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Script extends Asset {

    private String contents;

    public Script(String path) {
        super(path);
    }

    public String getContents() {
        return contents;
    }

    @Override
    protected void onLoad() {
        FileHandle fileHandle = Gdx.files.internal(path);
        contents = BaseEncoding.base64().encode(fileHandle.readBytes());
    }

    @Override
    protected void onUnload() {
        contents = null;
    }
}
