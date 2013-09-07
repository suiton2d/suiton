package com.nebula2d.editor.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
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
    PanelRenderer renderer;
    public RenderAdapter() {
        renderer = new PanelRenderer("test");
        //Texture tex = new Texture("C:\\Users\\bonazza\\Desktop\\972054_651885671491059_1198577050_n");
        //renderer.setTexture(tex);
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        SpriteBatch batcher = new SpriteBatch();
        batcher.begin();
        renderer.render(null, batcher);
        batcher.end();

    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
