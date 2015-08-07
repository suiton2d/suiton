package com.suiton2d.editor.ui.assets;

import com.suiton2d.editor.framework.FileNode;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class FileNodePopup extends JPopupMenu {

    public FileNodePopup(final FileNode fileNode) {
        JMenuItem deleteMenuItem = add("Delete");
        deleteMenuItem.addActionListener(e -> fileNode.remove());

        JMenuItem createDirMenuItem = add("Create Directory");
        createDirMenuItem.addActionListener(e -> new NewDirectoryDialog(fileNode));
    }
}
