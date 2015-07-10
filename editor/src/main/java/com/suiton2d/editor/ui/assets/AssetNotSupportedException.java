package com.suiton2d.editor.ui.assets;

public class AssetNotSupportedException extends RuntimeException {

    public AssetNotSupportedException(String ext) {
        super(String.format("The file extension %s is unsupported.", ext));
    }
}
