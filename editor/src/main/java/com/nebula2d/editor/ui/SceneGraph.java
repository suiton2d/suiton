/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.SceneNode;
import com.nebula2d.editor.ui.controls.N2DTree;
import com.nebula2d.scene.GameObject;
import com.nebula2d.scene.Layer;
import com.nebula2d.scene.Scene;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;

/**
 * The SceneGraph shows a visual representation of the current scene's Entity Hierarchy. Every direct decendant of
 * the root node is GUARANTEED to be a <i>Layer</i> object, while other children are guaranteed to be <i>Game Object</i>
 * objects.
 */
public class SceneGraph extends N2DTree {

    private DefaultMutableTreeNode root;

    private TreeDragSource dragSource;
    private TreeDropTarget dropTarget;

    public SceneGraph() {
        setRootVisible(false);
        setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        setBorder(null);
        dragSource = new TreeDragSource(this, DnDConstants.ACTION_MOVE);
        dropTarget = new TreeDropTarget(this);
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void init() {
        Scene currentScene = MainFrame.getProject().getCurrentScene();
        root = new SceneNode<>(currentScene.getName(), currentScene);

        //We need to create our own tree model so that we can override some default behaviour.
        DefaultTreeModel model = new DefaultTreeModel(root);
        model.addTreeModelListener(new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {
                //When a tree node has been edited, we need to update the SceneNode's name.
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getTreePath().getLastPathComponent();

                try {
                    int idx = e.getChildIndices()[0];
                    node = (DefaultMutableTreeNode) node.getChildAt(idx);
                } catch (NullPointerException ex) {
                    //Noop!
                }
                ((SceneNode)node).setName((String)node.getUserObject());
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {}

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {}

            @Override
            public void treeStructureChanged(TreeModelEvent e) {}
        });
        setModel(model);

        addTreeSelectionListener(e -> {
            //Don't want the new game object menu items to be enabled if nothing is selected.
            MainFrame.getN2DMenuBar().getGameObjectMenu().setEnabled(e.isAddedPath());

            if (e.isAddedPath()) {
                SceneNode selectedNode = (SceneNode) e.getPath().getLastPathComponent();
                if (selectedNode.getData() instanceof GameObject) {
                    MainFrame.getRenderCanvas().setSelectedObject((GameObject) selectedNode.getData());
                    return;
                }
            }

            MainFrame.getRenderCanvas().setSelectedObject(null);
        });

        setEditable(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        hideTreeIcons();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                SceneGraph graph = SceneGraph.this;
                TreePath clickedPath = graph.getClosestPathForLocation(e.getPoint().x, e.getPoint().y);
                if (clickedPath == null)
                    return;
                SceneNode clickedNode = (SceneNode) clickedPath.getLastPathComponent();
                graph.setSelectedNode(clickedNode);
                if (e.isPopupTrigger()) {
                    new SceneNodePopup(clickedNode).show(graph, e.getPoint().x, e.getPoint().y);
                }


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    new SceneNodePopup((SceneNode) SceneGraph.this.
                            getSelectionPath().getLastPathComponent()).
                            show(SceneGraph.this, e.getPoint().x, e.getPoint().y);
                }
            }
        });
    }

    private void hideTreeIcons() {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
    }

    public void setSelectedNode(DefaultMutableTreeNode node) {
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        TreeNode[] nodes = model.getPathToRoot(node);
        TreePath path = new TreePath(nodes);
        setSelectionPath(path);
    }

    /**
     * Add a Layer to the tree
     * @param layer the layer to be added
     */
    public void addLayer(Layer layer) {
        root.add(new SceneNode<>(layer.getName(), layer));
        refresh();
    }

    public int getGameObjectCount() {
        Enumeration children = root.depthFirstEnumeration();
        int count = 0;

        while (children.hasMoreElements()) {
            SceneNode node = (SceneNode) children.nextElement();
            if (node.getData() instanceof GameObject)
                ++count;
        }

        return count;
    }

    public int getLayerCount() {
        return root.getChildCount();
    }

    public void wipe() {
        if (root != null) {
            root.removeAllChildren();
            ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
        }
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

    private static class TreeDragSource implements DragSourceListener, DragGestureListener {

        private DragSource dragSource;
        private DragGestureRecognizer recognizer;

        private TransferableTreeNode transferable;

        private DefaultMutableTreeNode oldNode;

        private JTree sourceTree;

        public TreeDragSource(JTree sourceTree, int actions) {
            this.sourceTree = sourceTree;
            dragSource = new DragSource();
            recognizer = dragSource.createDefaultDragGestureRecognizer(sourceTree,
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
            transferable = new TransferableTreeNode(path);
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

    private static class TreeDropTarget implements DropTargetListener {
        private DropTarget target;

        private JTree targetTree;

        public TreeDropTarget(JTree targetTree) {
            this.targetTree = targetTree;
            target = new DropTarget(targetTree, this);
        }

        private TreeNode getNodeForEvent(DropTargetDragEvent dtde) {
            Point p = dtde.getLocation();
            DropTargetContext dtc = dtde.getDropTargetContext();
            JTree tree = (JTree) dtc.getComponent();
            TreePath path = tree.getClosestPathForLocation(p.x, p.y);
            return (TreeNode) path.getLastPathComponent();
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
            SceneGraph sceneGraph = (SceneGraph) dtc.getComponent();
            TreePath parentpath = sceneGraph.getClosestPathForLocation(pt.x, pt.y);
            SceneNode dest = (SceneNode) parentpath
                    .getLastPathComponent();

            try {
                Transferable tr = dtde.getTransferable();
                DataFlavor[] flavors = tr.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    if (tr.isDataFlavorSupported(flavor)) {
                        TreePath p = (TreePath) tr.getTransferData(flavor);
                        SceneNode node = (SceneNode) p
                                .getLastPathComponent();
                        if (node.getData() instanceof Layer)
                            break;


                        if (node.getData() instanceof GameObject) {
                            if (dest.getData() instanceof Layer)
                                ((Layer) dest.getData()).addGameObject((GameObject) node.getData());
                            else if (dest.getData() instanceof GameObject)
                                ((GameObject) dest.getData()).addActor((GameObject) node.getData());
                        }
                        dtde.acceptDrop(dtde.getDropAction());
                        dtde.dropComplete(true);
                        return;
                    }
                }
                dtde.rejectDrop();
            } catch (Exception e) {
                e.printStackTrace();
                dtde.rejectDrop();
                dtde.dropComplete(false);
            }
        }
    }


    private static class TransferableTreeNode implements Transferable {

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
}


