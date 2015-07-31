package com.suiton2d.editor.ui.assets;

import com.suiton2d.editor.framework.FileNode;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public class AssetsPane extends SuitonPanel {
    private AssetsTree assetsTree;
    private AssetsView assetsView;

    public AssetsPane() {
        super(new BorderLayout());
        assetsView = new AssetsView();
        assetsTree = new AssetsTree();
        assetsTree.addTreeSelectionListener(e -> {
            if (e.isAddedPath()) {
                FileNode selection = (FileNode)e.getPath().getLastPathComponent();
                assetsView.setPath(selection.getFile());
            }
        });
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle windowSize = ge.getMaximumWindowBounds();
        JScrollPane treeSp = new JScrollPane(assetsTree);
        JScrollPane avSp = new JScrollPane(assetsView);
        treeSp.setPreferredSize(new Dimension(windowSize.width / 6, windowSize.height / 4));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeSp, avSp);

        add(splitPane);
    }

    public AssetsTree getAssetsTree() {
        return assetsTree;
    }

    public AssetsView getAssetsView() {
        return assetsView;
    }
}
