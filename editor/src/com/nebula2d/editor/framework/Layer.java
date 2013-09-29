package com.nebula2d.editor.framework;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.Enumeration;

public class Layer extends BaseSceneNode implements ISaveable, ILoadable {

    public Layer(String name) {
        super(name);
    }

    public GameObject getGameObject(String name) {

        Enumeration children = depthFirstEnumeration();

        while (children.hasMoreElements()) {
            GameObject go = (GameObject) children.nextElement();
            if (go.getName().equals(name)) {
                return go;
            }
        }

        return null;
    }

    @Override
    public void remove() {
        MainFrame.getProject().getCurrentScene().removeLayer(this);
        super.remove();
    }

    public void render(GameObject selectedObject, SpriteBatch batcher) {

        Enumeration children = depthFirstEnumeration();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();

            if (node instanceof GameObject) {
                ((GameObject) node).render(selectedObject, batcher);
            }
        }
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        name = fr.readLine();
        int size = fr.readIntLine();
        for (int i = 0; i < size; ++i) {
            GameObject go = new GameObject("tmp");
            go.load(fr);
            add(go);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
        fw.writeIntLine(getChildCount());
        Enumeration children = children();
        while (children.hasMoreElements()) {
            GameObject go = (GameObject) children.nextElement();
            go.save(fw);
        }
    }
}
