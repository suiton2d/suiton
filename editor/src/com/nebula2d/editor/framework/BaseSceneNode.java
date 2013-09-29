package com.nebula2d.editor.framework;

import com.nebula2d.editor.ui.MainFrame;

import javax.swing.tree.DefaultMutableTreeNode;

public class BaseSceneNode extends DefaultMutableTreeNode {

    protected String name;

    public BaseSceneNode(String name) {
        this.name = name;
    }

    //region accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    public void addGameObject(GameObject go) {
        add(go);
        MainFrame.getSceneGraph().refresh();
    }

    public void remove() {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) getParent();
        parent.remove(this);
        MainFrame.getSceneGraph().refresh();
    }

    @Override
    public String toString() {
        return name;
    }
}
