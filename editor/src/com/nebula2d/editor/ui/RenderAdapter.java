package com.nebula2d.editor.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;

public class RenderAdapter implements ApplicationListener {

    //region members
    private GameObject selectedObject;
    private boolean enabled;
    //endregion

    //region accessors
    public GameObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(GameObject selectedObject) {
        this.selectedObject = selectedObject;
    }
    //endregion

    //region override methods from ApplicationListener
    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        SpriteBatch batcher = new SpriteBatch();
        batcher.begin();

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

    //endregion

    //region public methods
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    //endregion
}
