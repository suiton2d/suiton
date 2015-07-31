package com.suiton2d.editor.ui.controls;

import com.suiton2d.editor.ui.theme.N2DTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class SuitonTree extends JTree {

    protected DefaultMutableTreeNode root;

    public SuitonTree() {
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DTreeCellRenderer());
        setModel(null);
    }

    public void refresh() {
        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
        expandAll();
    }

    public void expandAll() {
        for (int i = 0; i < getRowCount(); ++i) {
            expandRow(i);
        }
    }
}
