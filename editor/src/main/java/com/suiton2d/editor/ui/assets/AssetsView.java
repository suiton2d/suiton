package com.suiton2d.editor.ui.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.suiton2d.assets.Asset;
import com.suiton2d.assets.Sprite;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jon on 7/1/2015.
 */
public class AssetsView extends SuitonPanel {

    private enum AssetExtensions {
        IMAGE(Arrays.asList("jpg", "jpeg", "png")),
        SOUND(Arrays.asList("ogg", "mp3", "wav")),
        TILEMAP(Collections.singletonList("tmx")),
        SCRIPT(Collections.singletonList("js"));

        private List<String> exts;
        AssetExtensions(List<String> exts) {
            this.exts = exts;
        }

        public boolean contains(String ext) {
            return exts.contains(ext.toLowerCase());
        }
    }

    public AssetsView() {
        super(new GridLayout(100, 100, 24, 24));
    }

    public void repopulate(Collection<Asset> assets) throws IOException {
        for (Asset asset : assets) {
            AssetIcon assetIcon = new AssetIcon(asset);
            add(assetIcon);
        }
    }
}
