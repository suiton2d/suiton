package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import com.nebula2d.util.ByteUtils;

import java.nio.charset.Charset;


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
        contents = ByteUtils.decodeBase64String(new String(fileHandle.readBytes()));
    }

    @Override
    protected void onUnload() {
        contents = null;
    }
}
