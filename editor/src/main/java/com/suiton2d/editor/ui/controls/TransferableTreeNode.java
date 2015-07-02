package com.suiton2d.editor.ui.controls;

import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableTreeNode implements Transferable {

    public static DataFlavor TREE_PATH_FLAVOR = new DataFlavor(TreePath.class,
            "Tree Path");

    private DataFlavor[] flavors = { TREE_PATH_FLAVOR };

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
        return flavor.getRepresentationClass() == TreePath.class;
    }

    @Override
    public synchronized Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor))
            throw new UnsupportedFlavorException(flavor);

        return treePath;
    }
}
