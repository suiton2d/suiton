package com.suiton2d.editor.ui.assets;

public enum AssetExtension {
    GRAPHIC(new String[]{"png", "jpg", "jpeg"}),
    SOUND(new String[] {"mp3", "ogg", "wav"}),
    SCRIPT(new String[] {"js"}),
    TILED(new String[]{"tmx"});

    private String[] exts;
    AssetExtension(String[] exts) {
        this.exts = exts;
    }

    public boolean contains(String target) {
        for (String s : exts) {
            if (s.equals(target))
                return true;
        }

        return false;
    }
}
