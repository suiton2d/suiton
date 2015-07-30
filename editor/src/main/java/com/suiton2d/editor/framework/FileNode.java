package com.suiton2d.editor.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * FileNode is a class representing a {@link javax.swing.tree.TreeNode} containing a pointer to a file.
 */
public class FileNode extends DefaultMutableTreeNode {

    public FileNode() {

    }

    /**
     * Constructor that associates a file with the node.
     * @param path The absolute path of a file to associate with the node.
     */
    public FileNode(String path) {
        setFile(path);
    }

    /**
     * Constructor that associates a file with a node
     * @param fileHandle the {@link FileHandle} to associate with the node.
     */
    public FileNode(FileHandle fileHandle) {
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
        removeFromParent();
    }

    public boolean addChildDirectory(String name) {
        FileHandle child = getFile().child(name);
        if (child.exists())
            return false;

        child.mkdirs();
        add(new FileNode(child));
        return true;
    }
}
