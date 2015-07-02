package com.suiton2d.editor.ui.controls;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.dnd.*;

public class TreeDragSource implements DragSourceListener, DragGestureListener {

    private DragSource dragSource;

    private DefaultMutableTreeNode oldNode;

    private JTree sourceTree;

    public TreeDragSource(JTree sourceTree, int actions) {
        this.sourceTree = sourceTree;
        dragSource = new DragSource();
        DragGestureRecognizer recognizer = dragSource.createDefaultDragGestureRecognizer(sourceTree,
                actions, this);
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        TreePath path = sourceTree.getSelectionPath();
        if ((path == null) || (path.getPathCount() <= 1)) {
            // We can't move the root node or an empty selection
            return;
        }
        oldNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        TransferableTreeNode transferable = new TransferableTreeNode(path);
        dragSource.startDrag(dge, DragSource.DefaultMoveNoDrop, transferable, this);
    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {

    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {

    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {

    }

    @Override
    public void dragExit(DragSourceEvent dse) {

    }

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        if (dsde.getDropSuccess()) {
            ((DefaultTreeModel) sourceTree.getModel()).removeNodeFromParent(oldNode);
        }
    }
}
