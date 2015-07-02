package com.suiton2d.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.suiton2d.editor.ui.controls.SuitonTree;
import com.suiton2d.editor.ui.controls.TreeDragSource;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
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
import java.io.File;
import java.util.List;

public class AssetsTree extends SuitonTree {

    private DefaultMutableTreeNode root;

    private TreeDragSource dragSource;
    private TreeDropTarget dropTarget;

    public AssetsTree() {
        setRootVisible(true);
        setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Assets")));
        setBorder(null);
        dropTarget = new TreeDropTarget(this);
        dragSource = new TreeDragSource(this, DnDConstants.ACTION_MOVE);
    }

    private static class TreeDropTarget implements DropTargetListener {
        private DropTarget target;

        private JTree targetTree;

        public TreeDropTarget(JTree targetTree) {
            this.targetTree = targetTree;
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
            DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) parentpath.getLastPathComponent();
            try {
                Transferable tr = dtde.getTransferable();
                for (DataFlavor flavor : tr.getTransferDataFlavors()) {
                    if (flavor.isFlavorJavaFileListType()) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);
                        FileHandle dest = Gdx.files.absolute(getPathString(parentpath));
                        List<File> fileList = (List<File>)tr.getTransferData(DataFlavor.javaFileListFlavor);
                        for (File f : fileList) {
                            if (f.isDirectory())
                                throw new Exception("Directory DnD not supported.");

                            FileHandle fileHandle = Gdx.files.absolute(f.getAbsolutePath());
                            fileHandle.copyTo(dest);
                            lastNode.add(new DefaultMutableTreeNode(fileHandle.file().getName()));
                        }
                        dtde.dropComplete(true);
                    } else if (flavor.getRepresentationClass().equals(TreePath.class)) {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                        FileHandle dest = Gdx.files.absolute(getPathString(parentpath));
                        TreePath p = (TreePath) tr.getTransferData(flavor);
                        FileHandle source = Gdx.files.absolute(getPathString(p));
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
            }
        }

        private String getPathString(TreePath path) {
            StringBuilder sb = new StringBuilder();
            Object[] paths = path.getPath();
            for (int i = 0; i < paths.length; ++i) {
                sb.append(paths[i]);
                if (i+1 < paths.length)
                    sb.append(File.separator);
            }

            return MainFrame.getProject().getProjectDir() + File.separator + sb.toString();
        }
    }
}
