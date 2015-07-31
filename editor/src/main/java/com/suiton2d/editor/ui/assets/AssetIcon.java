package com.suiton2d.editor.ui.assets;

import com.badlogic.gdx.files.FileHandle;
import com.suiton2d.editor.ui.controls.ImagePanel;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

public class AssetIcon extends SuitonPanel {

    @SuppressWarnings("all")
    private AssetIconDragSource dragSource;

    private FileHandle fileHandle;

    public AssetIcon(FileHandle fileHandle) throws IOException {
        super(new BorderLayout());
        this.fileHandle = fileHandle;
        this.dragSource = new AssetIconDragSource(this);
        ImagePanel imagePanel = new ImagePanel();
        JLabel filenameLbl = new JLabel(fileHandle.name());
        add(filenameLbl, BorderLayout.SOUTH);
        add(imagePanel);
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
