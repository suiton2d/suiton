package com.suiton2d.editor.ui.assets;

import com.badlogic.gdx.utils.Disposable;
import com.suiton2d.assets.Asset;
import com.suiton2d.editor.ui.controls.ImagePanel;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AssetIcon extends SuitonPanel implements Disposable {

    private Asset asset;

    public AssetIcon(Asset asset) throws IOException {
        super(new BorderLayout());
        this.asset = asset;
        ImagePanel imagePanel = new ImagePanel(asset.getPath());
        JLabel filenameLbl = new JLabel(asset.getFilename());
        add(filenameLbl, BorderLayout.SOUTH);
        add(imagePanel);
    }

    public Asset getAsset() {
        return asset;
    }

    @Override
    public void dispose() {
        if (asset != null)
            asset.dispose();
    }
}
