package com.suiton2d.editor.ui.assets;

import com.badlogic.gdx.files.FileHandle;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssetsView extends SuitonPanel {

    private static final int MAX_ROW_SIZE = 6;
    private List<AssetIcon> assetIcons = new ArrayList<>();
    private FileHandle path;

    public AssetsView() {
        super(new GridLayout(100, 100, 24, 24));
    }

    public void setPath(FileHandle path) {
        if (!path.exists())
            throw new RuntimeException(String.format("Path %s no longer exists.", path.path()));

        removeAll();
        FileHandle[] children = path.list(pathname -> !pathname.isDirectory());
        Dimension rowsColumns = getRowsColumns(children.length);
        setLayout(new GridLayout(rowsColumns.height, rowsColumns.width, 24, 24));
        for (FileHandle file : children) {
            AssetIcon icon;
            try {
                icon = new AssetIcon(file);
                icon.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        assetIcons.forEach(AssetIcon::deselect);
                        ((AssetIcon)e.getSource()).select();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        assetIcons.forEach(AssetIcon::deselect);
                        ((AssetIcon)e.getSource()).select();
                        if (e.isPopupTrigger())
                            new AssetPopup((AssetIcon)e.getSource()).show((AssetIcon)e.getSource(), e.getX(), e.getY());
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger())
                            new AssetPopup((AssetIcon)e.getSource()).show((AssetIcon)e.getSource(), e.getX(), e.getY());
                    }
                });
                assetIcons.add(icon);
                add(icon);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        revalidate();
        this.path = path;
    }

    public void resync() {
        if (path == null)
            throw new RuntimeException("Path not specified.");

        setPath(path);
    }

    private Dimension getRowsColumns(int numAssets) {
        int rows, cols;
        if (numAssets <= MAX_ROW_SIZE)
            return new Dimension(numAssets, 1);

        cols = MAX_ROW_SIZE;
        rows = (int)Math.ceil(numAssets/(double)MAX_ROW_SIZE);
        return new Dimension(cols, rows);
    }
}
