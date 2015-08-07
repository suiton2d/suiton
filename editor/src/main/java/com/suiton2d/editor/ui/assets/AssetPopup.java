package com.suiton2d.editor.ui.assets;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Container;

public class AssetPopup extends JPopupMenu {

    public AssetPopup(AssetIcon assetIcon) {
        JMenuItem deleteMenuItem = add("Delete");
        deleteMenuItem.addActionListener(e -> {
            assetIcon.getFileHandle().delete();
            Container parent = assetIcon.getParent();
            parent.remove(assetIcon);
            parent.revalidate();
        });
    }
}
