package com.suiton2d.editor.ui.assets;

import com.suiton2d.editor.ui.controls.FileListTransferable;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

public class AssetIconDragSource implements DragSourceListener, DragGestureListener {

    @SuppressWarnings("all")
    private DragSource dragSource;
    private AssetIcon assetIcon;

    public AssetIconDragSource(AssetIcon assetIcon) {
        this.assetIcon = assetIcon;
        dragSource = new DragSource();

        @SuppressWarnings("unused")
        DragGestureRecognizer recognizer = dragSource.createDefaultDragGestureRecognizer(assetIcon,
                DnDConstants.ACTION_REFERENCE, this);
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        Transferable transferable = new FileListTransferable(assetIcon.getFileHandle());
        dge.startDrag(null, transferable, this);
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

    }
}
