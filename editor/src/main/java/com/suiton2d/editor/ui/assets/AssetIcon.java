package com.suiton2d.editor.ui.assets;

import com.badlogic.gdx.files.FileHandle;
import com.suiton2d.editor.ui.controls.ImagePanel;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

public class AssetIcon extends JPanel {

    @SuppressWarnings("all")
    private AssetIconDragSource dragSource;

    private FileHandle fileHandle;

    public AssetIcon(FileHandle fileHandle) throws IOException {
        super(new BorderLayout());
        this.fileHandle = fileHandle;
        this.dragSource = new AssetIconDragSource(this);
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setImage(getImageFileForAsset());
        JLabel filenameLbl = new JLabel(fileHandle.name());
        add(filenameLbl, BorderLayout.SOUTH);
        add(imagePanel);
    }

    @SuppressWarnings("all")
    private FileHandle getImageFileForAsset() {
        String ext = fileHandle.extension();
        if (AssetExtension.GRAPHIC.contains(ext)) {
            // TODO: Load graphic type icon.
        } else if (AssetExtension.SCRIPT.contains(ext)) {
            // TODO: Load script type icon.
        } else if (AssetExtension.SOUND.contains(ext)) {
            // TODO: Load sound type icon.
        } else if (AssetExtension.TILED.contains(ext)) {
            // TODO: Load tiled type icon.
        }

        return null;
    }

    public void deselect() {
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void select() {
        setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 128), 2));
    }

    public FileHandle getFileHandle() {
        return fileHandle;
    }
}
