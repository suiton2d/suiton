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

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nebula2d.editor.framework.BaseSceneNode;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Scene;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RenderCanvas extends LwjglAWTCanvas implements MouseListener, MouseMotionListener, MouseWheelListener {

    protected RenderAdapter adapter;

    protected Point lastPoint;

    protected boolean isMouseDown;

    public RenderCanvas(RenderAdapter adapter) {
        super(adapter);
        this.adapter = adapter;
        this.getCanvas().addMouseListener(this);
        this.getCanvas().addMouseMotionListener(this);
        this.getCanvas().addMouseWheelListener(this);
        this.isMouseDown = false;
    }

    public void setEnabled(boolean enabled) {
        adapter.setEnabled(enabled);
    }

    public OrthographicCamera getCamera() {
        return adapter.getCamera();
    }

    protected List<GameObject> getSelectedGameObjects(int x, int y) {

        List<GameObject> res = new ArrayList<GameObject>();

        Scene scene = MainFrame.getProject().getCurrentScene();
        Enumeration nodes = scene.depthFirstEnumeration();

        while (nodes.hasMoreElements()) {
            BaseSceneNode currentNode = (BaseSceneNode) nodes.nextElement();
            if (currentNode instanceof GameObject) {
                GameObject g = (GameObject) currentNode;
                Camera cam = getCamera();

                if (g.isSelected(cam, x, cam.viewportHeight - y))
                    res.add(g);
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

        float newX = adapter.getSelectedObject().getPosition().x + dx * getCamera().zoom;
        float newY = adapter.getSelectedObject().getPosition().y - dy * getCamera().zoom;
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

    public void setSelectedObject(GameObject go) {
        this.adapter.setSelectedObject(go);
    }

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
            getCamera().translate(dx, dy);
            getCamera().update();


        } else if (selectedObject != null && selectedObject.isMoveable()) {
             transformObject(pos);
        }

        lastPoint = pos;
    }

    private void transformObject(Point mousPos) {
        switch (MainFrame.getToolbar().getSelectedRendererWidget()) {
            case N2DToolbar.RENDERER_WIDGET_TRANSLATE:
                translateObject(mousPos);
                break;
            case N2DToolbar.RENDERER_WIDGET_SCALE:
                scaleObject(mousPos);
                break;
            case N2DToolbar.RENDERER_WIDGET_ROTATE:
                rotateObject(mousPos);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        getCamera().zoom += e.getWheelRotation() * 0.25f;
    }
}
