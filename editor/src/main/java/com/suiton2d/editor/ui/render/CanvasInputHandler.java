package com.suiton2d.editor.ui.render;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.suiton2d.editor.framework.SceneNode;
import com.suiton2d.editor.framework.Selection;
import com.suiton2d.editor.ui.MainFrame;
import com.suiton2d.editor.ui.SuitonToolbar;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class CanvasInputHandler extends InputAdapter {
    private Vector2 lastPoint = new Vector2();
    private boolean controlDown = false;
    private MainFrame mainFrame;
    private RenderCanvas renderCanvas;
    private SceneManager sceneManager;

    public CanvasInputHandler(MainFrame mainFrame, RenderCanvas renderCanvas, SceneManager sceneManager) {
        this.mainFrame = mainFrame;
        this.renderCanvas = renderCanvas;
        this.sceneManager = sceneManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT) {
            controlDown = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT) {
            controlDown = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            lastPoint.set(screenX, screenY);

            if (!controlDown) {
                List<Selection> selectedObjects = getSelectedGameObjects(lastPoint.x, lastPoint.y);
                int size = selectedObjects.size();
                if (size > 0) {
                    GameObject selectedObject = selectedObjects.get(size - 1).getGameObject();
                    mainFrame.getSceneGraph().setSelectedNode(new SceneNode<>(mainFrame.getSceneGraph(),
                            selectedObject.getName(), selectedObject));
                } else {
                    mainFrame.getSceneGraph().setSelectionPath(null);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        GameObject selectedObject = renderCanvas.getSelectedObject().getGameObject();
        Vector2 pos = new Vector2(screenX, screenY);
        if (controlDown) {
            int dx = (screenX - (int)lastPoint.x) / 2;
            int dy = -(screenY - (int)lastPoint.y) / 2;
            Camera cam = sceneManager.getCurrentScene().getCamera();
            cam.translate(dx, dy, 0.0f);
            cam.update();
            lastPoint = pos;
            return true;
        }

        if (selectedObject != null) {
            transformObject(pos);
            lastPoint = pos;
            return true;
        }

        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        ((OrthographicCamera)sceneManager.getCurrentScene().getCamera()).zoom += amount * 0.25f;
        return true;
    }

    protected List<Selection> getSelectedGameObjects(float x, float y) {

        List<Selection> res = new ArrayList<>();

        if (mainFrame.getProject() == null)
            return res;

        Scene scene = sceneManager.getCurrentScene();
        if (scene != null) {
            for (Actor layer : scene.getChildren()) {
                for (Actor gameObject : ((Layer)layer).getChildren())
                    searchGameObjectChildrenForSelection((GameObject)gameObject, res, x, y);
            }
        }

        return res;
    }

    private void searchGameObjectChildrenForSelection(GameObject gameObject, List<Selection> selections, float x, float y) {
        OrthographicCamera cam = (OrthographicCamera) sceneManager.getCurrentScene().getCamera();
        if (isGameObjectSelected(gameObject, cam, x, cam.viewportHeight-y))
            selections.add(new Selection(gameObject));

        for (Actor child : gameObject.getChildren()) {
            GameObject g = (GameObject) child;
            searchGameObjectChildrenForSelection(g, selections, x, y);
        }
    }

    private boolean isGameObjectSelected(GameObject gameObject, OrthographicCamera cam, float x, float y) {
        Selection maybeSelection = new Selection(gameObject);
        if (gameObject.getRenderer() != null && maybeSelection.getBoundingBox(cam).contains(x, y))
            return true;

        Vector3 proj = cam.project(new Vector3(gameObject.getX(), gameObject.getY(), 0));
        Circle point = new Circle(proj.x, proj.y, 4);
        return point.contains(x, y);
    }

    private void transformObject(Vector2 mousPos) {
        switch (mainFrame.getSuitonToolbar().getSelectedRendererWidget()) {
            case SuitonToolbar.RENDERER_WIDGET_TRANSLATE:
                translateObject(mousPos);
                break;
            case SuitonToolbar.RENDERER_WIDGET_SCALE:
                scaleObject(mousPos);
                break;
            case SuitonToolbar.RENDERER_WIDGET_ROTATE:
                rotateObject(mousPos);
                break;
        }
    }

    protected void translateObject(Vector2 mousePos) {
        int dx = (int)(mousePos.x - lastPoint.x);
        int dy = (int)(mousePos.y - lastPoint.y);
        renderCanvas.getSelectedObject().getGameObject().moveBy(dx, -dy);
    }

    protected void scaleObject(Vector2 mousePos) {
        int dx = (int)(mousePos.x - lastPoint.x);
        int dy = (int)(mousePos.y - lastPoint.y);
        renderCanvas.getSelectedObject().getGameObject().scaleBy(dx, dy);
    }

    protected void rotateObject(Vector2 mousePos) {
        int dy = (int)(mousePos.y - lastPoint.y);
        renderCanvas.getSelectedObject().getGameObject().rotateBy(dy*0.5f);
    }
}
