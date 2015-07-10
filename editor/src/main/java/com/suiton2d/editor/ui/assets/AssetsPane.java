package com.suiton2d.editor.ui.assets;

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
        assetsTree = new AssetsTree();
        assetsTree.addTreeSelectionListener(e -> {
            // TODO: populate right panel.
        });
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenSize = ge.getMaximumWindowBounds();
        assetsView = new AssetsView();
        JScrollPane sp = new JScrollPane(assetsTree);
        sp.setPreferredSize(new Dimension(screenSize.width/6, screenSize.height/4));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, assetsView);

        add(splitPane);
    }
}
