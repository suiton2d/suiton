package com.suiton2d.editor.ui.assets;

import com.badlogic.gdx.files.FileHandle;
import com.suiton2d.editor.framework.FileNode;
import com.suiton2d.editor.ui.MainFrame;
import com.suiton2d.editor.ui.controls.SuitonTree;
import com.suiton2d.editor.ui.controls.TreeDragSource;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AssetsTree extends SuitonTree {

    private FileNode root;

    @SuppressWarnings("all")
    private TreeDragSource dragSource;
    @SuppressWarnings("all")
    private TreeDropTarget dropTarget;

    private boolean initialized = false;

    public void init() {
        setRootVisible(true);
        root = new FileNode(this, MainFrame.getProject().getAssetsDirPath());
        setModel(new DefaultTreeModel(root));
        setBorder(null);
        dropTarget = new TreeDropTarget(this);
        dragSource = new TreeDragSource(this, DnDConstants.ACTION_MOVE);
        populateChildren(root);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AssetsTree tree = AssetsTree.this;
                TreePath clickedPath = tree.getClosestPathForLocation(e.getX(), e.getY());
                if (clickedPath == null)
                    return;
                FileNode fileNode = (FileNode) clickedPath.getLastPathComponent();
                tree.setSelectionPath(clickedPath);
                if (e.isPopupTrigger())
                    new FileNodePopup(fileNode).show(tree, e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    new FileNodePopup((FileNode) AssetsTree.this.getSelectionPath().
                            getLastPathComponent()).show(AssetsTree.this, e.getPoint().x, e.getPoint().y);
                }
            }
        });
        expandAll();
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void expandAll() {
        for (int i = 0; i < getRowCount(); ++i) {
            expandRow(i);
        }
    }

    private void populateChildren(FileNode root) {
        FileHandle[] childDirs = root.getFile().list(File::isDirectory);
        for (FileHandle dir : childDirs) {
            FileNode child = new FileNode(this, dir);
            populateChildren(child);
            root.add(child);
        }
    }

    @SuppressWarnings("unused")
    public void refresh() {
        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }

    private static class TreeDropTarget implements DropTargetListener {

        @SuppressWarnings("all")
        private DropTarget target;

        public TreeDropTarget(JTree targetTree) {
            target = new DropTarget(targetTree, this);
        }

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            dtde.acceptDrag(dtde.getDropAction());
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            dtde.acceptDrag(dtde.getDropAction());
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {

        }

        @Override
        public void dragExit(DropTargetEvent dte) {

        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            Point pt = dtde.getLocation();
            DropTargetContext dtc = dtde.getDropTargetContext();
            AssetsTree assetsTree = (AssetsTree) dtc.getComponent();
            TreePath parentpath = assetsTree.getClosestPathForLocation(pt.x, pt.y);
            FileNode selectedTarget = (FileNode)parentpath.getLastPathComponent();
            try {
                Transferable tr = dtde.getTransferable();
                for (DataFlavor flavor : tr.getTransferDataFlavors()) {
                    if (dtde.isDataFlavorSupported(flavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                        FileHandle dest = selectedTarget.getFile();
                        TreePath p = (TreePath) tr.getTransferData(flavor);
                        FileNode sourceNode = (FileNode)p.getLastPathComponent();
                        FileHandle source = sourceNode.getFile();
                        source.moveTo(dest);
                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                        dtde.dropComplete(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                dtde.rejectDrop();
                dtde.dropComplete(false);
                JOptionPane.showMessageDialog(assetsTree.getRootPane(), e.getMessage());
            }
        }
    }
}
