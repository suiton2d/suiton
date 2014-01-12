package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.BaseSceneNode;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Layer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * The SceneGraph shows a visual representation of the current scene's Entity Hierarchy. Every direct decendant of
 * the root node is GUARANTEED to be a <i>Layer</i> object, while other children are guaranteed to be <i>Game Object</i>
 * objects.
 */
public class SceneGraph extends JTree {

    private DefaultMutableTreeNode root;

    public SceneGraph() {
        setRootVisible(false);
        setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
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
        //hideTreeIcons();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                SceneGraph graph = SceneGraph.this;
                TreePath clickedPath = graph.getClosestPathForLocation(e.getPoint().x, e.getPoint().y);
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

    /**
     * hides the icons on the JTree nodes
     */
    private void hideTreeIcons() {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
    }

    public DefaultMutableTreeNode getRoot() {
        return this.root;
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
        MainFrame.getProject().getCurrentScene().add(layer);
        refresh();
    }

    public void updateSelection(GameObject go, Object rootData) {

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
        root.removeAllChildren();
        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }

    public List<Layer> getAllLayers() {
        List<Layer> layers = new ArrayList<Layer>();
        Enumeration<DefaultMutableTreeNode> children = root.children();

        while(children.hasMoreElements()) {
            layers.add((Layer)children.nextElement().getUserObject());
        }

        return layers;
    }

    public List<String> getAllLayerNames() {
        List<String> layerNames = new ArrayList<String>();
        Enumeration<DefaultMutableTreeNode> children = root.children();

        while(children.hasMoreElements()) {
            layerNames.add(((Layer)children.nextElement().getUserObject()).getName());
        }

        return layerNames;
    }

    public Layer getLayer(String name) {
        Enumeration<DefaultMutableTreeNode> children = root.children();

        while (children.hasMoreElements()) {
            Layer layer = (Layer)children.nextElement().getUserObject();

            if (layer.getName().equals(name))
                return layer;
        }

        return null;
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
