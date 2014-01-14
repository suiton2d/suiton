package com.nebula2d.editor.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.components.Animation;

/**
 * Created by bonazza on 1/11/14.
 */
public class AnimationRenderAdapter implements ApplicationListener {

    private Animation animation;
    private OrthographicCamera camera;

    public AnimationRenderAdapter(Animation animation) {
        this.animation = animation;
    }

    public void initCamera(int w, int h) {
        camera = new OrthographicCamera(w, h);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i2) {

    }

    @Override
    public void render() {
        Gdx.graphics.getGL20().glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        SpriteBatch badger = new SpriteBatch();

        badger.begin();
        animation.renderAnimated(badger, camera);
        badger.end();
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

    public Animation getAnimation() {
        return animation;
    }
}
