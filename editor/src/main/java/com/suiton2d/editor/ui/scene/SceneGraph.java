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

package com.suiton2d.editor.ui.scene;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.suiton2d.editor.framework.SceneNode;
import com.suiton2d.editor.ui.MainFrame;
import com.suiton2d.editor.ui.controls.SuitonTree;
import com.suiton2d.editor.ui.controls.TreeDragSource;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneManager;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
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
import java.util.Enumeration;

/**
 * The SceneGraph shows a visual representation of the current scene's Entity Hierarchy. Every direct decendant of
 * the root node is GUARANTEED to be a <i>Layer</i> object, while other children are guaranteed to be <i>Game Object</i>
 * objects.
 */
public class SceneGraph extends SuitonTree {

    @SuppressWarnings("all")
    private TreeDragSource dragSource;
    @SuppressWarnings("all")
    private TreeDropTarget dropTarget;
    private MainFrame mainFrame;

    public SceneGraph(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        dragSource = new TreeDragSource(this, DnDConstants.ACTION_MOVE);
        dropTarget = new TreeDropTarget(this);
        setRootVisible(false);
        setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        setBorder(null);
    }

    public void init(SceneManager sceneManager) {
        Scene currentScene = sceneManager.getCurrentScene();
        root = new SceneNode<>(this, currentScene.getName(), currentScene);

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
            mainFrame.getSuitonMenuBar().getGameObjectMenu().setEnabled(e.isAddedPath());

            if (e.isAddedPath()) {
                SceneNode selectedNode = (SceneNode) e.getPath().getLastPathComponent();
                if (selectedNode.getData() instanceof GameObject) {
                    mainFrame.getRenderCanvas().setSelectedObject((GameObject) selectedNode.getData());
                    return;
                }
            }
            mainFrame.getRenderCanvas().setSelectedObject(null);
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
        SceneNode<Layer> layerNode = new SceneNode<>(this, layer.getName(), layer);
        layer.getChildren().forEach(go -> layerNode.add(new SceneNode<>(this, go.getName(), (Group)go)));
        root.add(layerNode);
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

                        if (node.getData() instanceof GameObject)
                            dest.add(node);

                        dtde.acceptDrop(dtde.getDropAction());
                        dtde.dropComplete(true);
                        return;
                    }
                }
                dtde.rejectDrop();
                dtde.dropComplete(false);
            } catch (Exception e) {
                e.printStackTrace();
                dtde.rejectDrop();
                dtde.dropComplete(false);
            }
        }
    }
}


