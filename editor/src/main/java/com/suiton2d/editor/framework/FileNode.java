package com.suiton2d.editor.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * FileNode is a class representing a {@link javax.swing.tree.TreeNode} containing a pointer to a file.
 */
public class FileNode extends DefaultMutableTreeNode {

    private JTree parentTree;

    public FileNode(JTree parentTree) {
        this.parentTree = parentTree;
    }

    /**
     * Constructor that associates a file with the node.
     * @param path The absolute path of a file to associate with the node.
     */
    public FileNode(JTree parentTree, String path) {
        this(parentTree);
        setFile(path);
    }

    /**
     * Constructor that associates a file with a node
     * @param fileHandle the {@link FileHandle} to associate with the node.
     */
    public FileNode(JTree parentTree, FileHandle fileHandle) {
        this(parentTree);
        setFile(fileHandle);
    }

    public void setFile(String path) {
        setFile(Gdx.files.absolute(path));
    }

    public void setFile(FileHandle file) {
        super.setUserObject(file);
    }

    public FileHandle getFile() {
        return (FileHandle)super.getUserObject();
    }

    /**
     * This method shouldn't be used. {@link FileNode#getFile()} should be used instead.
     * @throws RuntimeException when accessed.
     */
    @Override
    public Object getUserObject() {
        throw new RuntimeException("Use getFile() instead.");
    }

    /**
     * This method shouldn't be used. {@link FileNode#setFile(String)} should be used instead.
     * @throws RuntimeException when accessed.
     */
    @Override
    public void setUserObject(Object userObject) {
        throw new RuntimeException("User setFile() instead.");
    }

    @Override
    public String toString() {
        return getFile().name();
    }

    public void remove() {
        getFile().deleteDirectory();
        TreeNode parent = getParent();
        removeFromParent();
        ((DefaultTreeModel)parentTree.getModel()).nodeStructureChanged(parent);
    }

    public boolean addChildDirectory(String name) {
        FileHandle child = getFile().child(name);
        if (child.exists())
            return false;

        child.mkdirs();
        add(new FileNode(parentTree, child));
        ((DefaultTreeModel)parentTree.getModel()).nodeStructureChanged(this);
        return true;
    }
}
