package com.suiton2d.editor.ui.controls;

import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableTreeNode implements Transferable {

    private DataFlavor[] flavors = { new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
            + "; class=java.lang.Object", "Local Domain Object") };

    private TreePath treePath;

    public TransferableTreeNode(TreePath treePath) {
        this.treePath = treePath;
    }
    @Override
    public synchronized DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (DataFlavor df : flavors) {
            if (df.equals(flavor))
                return true;

        }
        return false;
    }

    @Override
    public synchronized Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor))
            throw new UnsupportedFlavorException(flavor);

        return treePath;
    }
}
