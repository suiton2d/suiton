package com.suiton2d.editor.ui.controls;

import javax.swing.tree.TreePath;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

public class TreeDragSource implements DragSourceListener, DragGestureListener {

    private DragSource dragSource;

    private SuitonTree sourceTree;

    public TreeDragSource(SuitonTree sourceTree, int actions) {
        this.sourceTree = sourceTree;
        dragSource = new DragSource();

        @SuppressWarnings("unused")
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

        TransferableTreeNode transferable = new TransferableTreeNode(path);
        dragSource.startDrag(dge, DragSource.DefaultMoveDrop, transferable, this);
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
            sourceTree.refresh();
        }
    }
}
