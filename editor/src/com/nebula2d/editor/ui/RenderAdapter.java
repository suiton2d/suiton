package com.nebula2d.editor.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Project;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.framework.components.PanelRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 8/20/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class RenderAdapter implements ApplicationListener {

    private GameObject selectedObject;
    private boolean enabled;

    public RenderAdapter() {

    }
    //region accessors
    public GameObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(GameObject selectedObject) {
        this.selectedObject = selectedObject;
    }
    //endregion
    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.graphics.getGL20().glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch batcher = new SpriteBatch();
        batcher.begin();
        Project p = MainFrame.getProject();
        if (p != null) {
            p.getCurrentScene().render(selectedObject, batcher);
        }
        batcher.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
