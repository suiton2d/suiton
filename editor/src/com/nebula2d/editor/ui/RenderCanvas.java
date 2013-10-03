package com.nebula2d.editor.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Layer;
import com.nebula2d.editor.framework.Scene;


import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RenderCanvas extends LwjglAWTCanvas implements MouseListener, MouseMotionListener{

    //region members
    protected float camXOffset, camYOffset;

    protected RenderAdapter adapter;

    protected Point lastPoint;

    protected boolean isMouseDown;
    //endregion

    //region constructors
    public RenderCanvas(RenderAdapter adapter) {
        super(adapter, true);
        this.adapter = adapter;
        this.getCanvas().addMouseListener(this);
        this.getCanvas().addMouseMotionListener(this);
        this.isMouseDown = false;
    }
    //endregion
    //region public methods
    public void setEnabled(boolean enabled) {
        getCanvas().setEnabled(enabled);
        adapter.setEnabled(enabled);
    }
    //endregion

    public OrthographicCamera getCamera() {
        return adapter.getCamera();
    }
    //region internal methods
    protected List<GameObject> getSelectedGameObjects(int x, int y) {

        List<GameObject> res = new ArrayList<GameObject>();

        Scene scene = MainFrame.getProject().getCurrentScene();
        for (Layer l : scene.getLayers()) {
            Enumeration gameObjects = l.depthFirstEnumeration();
            while (gameObjects.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) gameObjects.nextElement();
                if (node instanceof GameObject) {
                    GameObject g = (GameObject) node;
                    Vector2 scale = g.getScale();
                    int w = g.getRenderer().getBoundingWidth();
                    int h = g.getRenderer().getBoundingHeight();
                    Camera cam = adapter.getCamera();
                    System.out.println(g.getName());

                    if (g.getRenderer() != null) {
                        System.out.println("renderer is not null");
                        System.out.println("center: " + g.getPosition().x + " " + g.getPosition().y);

                        Rectangle boundingBox = g.getRenderer().getBoundingBox();
                        if (boundingBox.contains(new Vector2(x + cam.position.x, (getCanvas().getHeight() - y) + cam.position.y))) {
                            res.add(g);
                        }
                    }
                }

            }
        }


        return res;
    }

    public void initCamera(int w, int h) {
        adapter.initCamera(w, h);
    }

    protected void translateObject(Point mousePos) {
        int dx = mousePos.x - lastPoint.x;
        int dy = mousePos.y - lastPoint.y;

        float newX = adapter.getSelectedObject().getPosition().x + dx;
        float newY = adapter.getSelectedObject().getPosition().y - dy;
        adapter.getSelectedObject().setPosition(newX, newY);
    }

    protected void scaleObject(Point mousePos) {
        int dx = mousePos.x - lastPoint.x;
        int dy = mousePos.y - lastPoint.y;

        float newX = adapter.getSelectedObject().getScale().x + dx;
        float newY = adapter.getSelectedObject().getScale().y - dy;

        adapter.getSelectedObject().setScale(newX, newY);
    }

    protected void rotateObject(Point mousePos) {
        int dy = mousePos.y - lastPoint.y;
        float newY = adapter.getSelectedObject().getRotation() + dy * 0.5f;
        adapter.getSelectedObject().setRotation(newY);
    }
    //endregion

    //region accessors
    public float getCamXOffset() {
        return camXOffset;
    }

    public float getCamYOffset() {
        return camYOffset;
    }

    public GameObject getSelectedObject() {
        return adapter.getSelectedObject();
    }

    public void setSelectedObject(GameObject go) {
        this.adapter.setSelectedObject(go);
    }
    //endregion

    //region overrided methods from MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPoint = e.getPoint();
            if (e.isControlDown()) {
                return;
            }

            List<GameObject> selectedObjects = getSelectedGameObjects(lastPoint.x, lastPoint.y);
            int size = selectedObjects.size();
            System.out.println(size);
            if (size > 0) {
                GameObject selectedObject = selectedObjects.get(size - 1);
                MainFrame.getSceneGraph().setSelectedNode(selectedObject);
            } else {
                MainFrame.getSceneGraph().setSelectionPath(null);
            }
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {}
    //endregion

    //region overrided methods from MouseMotionListener
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pos = e.getPoint();
        GameObject selectedObject = adapter.getSelectedObject();
        if (e.isControlDown()) {
            int dx = (pos.x - lastPoint.x) / 2;
            int dy = -(pos.y - lastPoint.y) / 2;
            /*camXOffset += dx;
            camYOffset += dy;*/
            System.out.println("test");
            getCamera().translate(dx, dy);
            System.out.println("x: " + dx + " | y: " + dy);


        } else if (selectedObject != null) {
            switch (MainFrame.getToolbar().getSelectedRendererWidget()) {
                case N2DToolbar.RENDERER_WIDGET_TRANSLATE:
                    translateObject(pos);
                    break;
                case N2DToolbar.RENDERER_WIDGET_SCALE:
                    scaleObject(pos);
                    break;
                case N2DToolbar.RENDERER_WIDGET_ROTATE:
                    rotateObject(pos);
                    break;
            }
        }

        lastPoint = pos;

    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    //endregion
}
