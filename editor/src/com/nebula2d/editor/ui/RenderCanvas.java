package com.nebula2d.editor.ui;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.nebula2d.editor.framework.GameObject;


import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
        //this.getCanvas().setMinimumSize(new Dimension(600,600));
    }
    //endregion

    //region public methods
    public void setEnabled(boolean enabled) {
        getCanvas().setEnabled(enabled);
        adapter.setEnabled(enabled);
    }
    //endregion

    //region internal methods
    protected List<GameObject> getSelectedGameObjects(int x, int y) {

        List<GameObject> res = null;
        //TODO: finish this method

        return res;
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

    protected boolean checkPointInRect(int w, int h, Point center, Point posi, float rot) {
        double rotRad = Math.PI * rot / 180.0f;
        float tx = posi.x - center.x;
        float ty = posi.y - center.y;
        double dx = tx * Math.cos(rotRad) - ty * Math.sin(rotRad);
        double dy = tx * Math.sin(rotRad) - ty * Math.cos(rotRad);

        dx += center.x;
        dy += center.y;

        return (dx > center.x - w/2 && dx < center.x + w/2 && dy > center.y - h/2 && dy < center.y + h/2);
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
            int size = selectedObjects != null ? selectedObjects.size() : 0;

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
            int dx = pos.x - lastPoint.x;
            int dy = pos.y - lastPoint.y;
            camXOffset += dx;
            camYOffset += dy;
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
