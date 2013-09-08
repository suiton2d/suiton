package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Layer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SceneGraph extends JTree {

    //region members
    private DefaultMutableTreeNode root;
    //endregion

    //region constructors
    public SceneGraph() {

        setRootVisible(false);
        root = new DefaultMutableTreeNode("SceneGraph");
        DefaultTreeModel model = new DefaultTreeModel(root);
        setModel(model);
    }
    //endregion

    //region public methods
    public void addLayer(Layer layer) {
        root.add(new DefaultMutableTreeNode(layer));
        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }

    public void addGameObject(Layer layer, GameObject gameObject) {
        Enumeration<DefaultMutableTreeNode> children = root.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = children.nextElement();
            if (node.getUserObject() == layer) {
                node.add(new DefaultMutableTreeNode(gameObject));
                break;
            }
        }

        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }

    public void removeNode(Object rootData) {
        Enumeration<DefaultMutableTreeNode> children = root.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = children.nextElement();
            if (node.getUserObject() == rootData) {
                node.removeFromParent();
                break;
            }
        }

        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }

    public void removeChildren(Object rootData) {
        Enumeration<DefaultMutableTreeNode> children = root.children();

        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = children.nextElement();
            if (node.getUserObject() == rootData) {
                node.removeAllChildren();
            }
        }

        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }

    public void updateSelection(GameObject go, Object rootData) {

    }

    public void wipe() {
        root.removeAllChildren();
        ((DefaultTreeModel)getModel()).nodeStructureChanged(root);
    }
    //endregion

    //region accessors
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
    //endregion
}
