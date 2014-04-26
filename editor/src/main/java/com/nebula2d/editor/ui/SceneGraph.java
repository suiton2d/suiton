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

import com.nebula2d.editor.framework.BaseSceneNode;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Layer;
import com.nebula2d.editor.ui.controls.N2DTree;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 * The SceneGraph shows a visual representation of the current scene's Entity Hierarchy. Every direct decendant of
 * the root node is GUARANTEED to be a <i>Layer</i> object, while other children are guaranteed to be <i>Game Object</i>
 * objects.
 */
public class SceneGraph extends N2DTree {

    private DefaultMutableTreeNode root;

    public SceneGraph() {
        setRootVisible(false);
        setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
//        Border outterBorder = new MatteBorder(0, 0, 0, 0, new Color(40, 40, 40));
//        Border innerBorder = new MatteBorder(0, 0, 0, 0, new Color(80, 80, 80));
//        setBorder(BorderFactory.createCompoundBorder(outterBorder, innerBorder));
        setBorder(null);
    }

    public void init() {
        root = MainFrame.getProject().getCurrentScene();

        //We need to create our own tree model so that we can override some default behaviour.
        DefaultTreeModel model = new DefaultTreeModel(root);
        model.addTreeModelListener(new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {
                //When a tree node has been edited, we need to update the BaseSceneNode's name.
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getTreePath().getLastPathComponent();

                try {
                    int idx = e.getChildIndices()[0];
                    node = (DefaultMutableTreeNode) node.getChildAt(idx);
                } catch (NullPointerException ex) {
                    //Noop!
                }
                ((BaseSceneNode)node).setName((String)node.getUserObject());
                System.out.println(((BaseSceneNode)node).getName());
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {}

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {}

            @Override
            public void treeStructureChanged(TreeModelEvent e) {}
        });
        setModel(model);

        addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                //Don't want the new game object menu items to be enabled if nothing is selected.
                MainFrame.getN2DMenuBar().getGameObjectMenu().setEnabled(e.isAddedPath());

                if (e.isAddedPath()) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                    if (selectedNode instanceof GameObject) {
                        MainFrame.getRenderCanvas().setSelectedObject((GameObject) selectedNode);
                        MainFrame.getToolbar().getComponentsButton().setEnabled(true);
                        return;
                    }
                }

                MainFrame.getToolbar().getComponentsButton().setEnabled(false);
                MainFrame.getRenderCanvas().setSelectedObject(null);
            }
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
                BaseSceneNode clickedNode = (BaseSceneNode) clickedPath.getLastPathComponent();
                graph.setSelectedNode(clickedNode);
                if (e.isPopupTrigger()) {
                    new SceneNodePopup(clickedNode).show(graph, e.getPoint().x, e.getPoint().y);
                }


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    new SceneNodePopup((BaseSceneNode) SceneGraph.this.
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
        root.add(layer);
        refresh();
    }

    public int getGameObjectCount() {
        Enumeration children = root.depthFirstEnumeration();
        int count = 0;

        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            if (node instanceof GameObject)
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
}
