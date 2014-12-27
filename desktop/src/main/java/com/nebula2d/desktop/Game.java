package com.nebula2d.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nebula2d.assets.AssetManager;
import com.nebula2d.scene.Scene;
import com.nebula2d.scene.SceneManager;

/**
 *
 * Created by bonazza on 10/10/14.
 */
public class Game implements ApplicationListener {

    @Override
    public void create() {
        FileHandle assetsFile = Gdx.files.internal("assets.xml");
        FileHandle scenesFile = Gdx.files.internal("scenes.xml");
        AssetManager.init(new ClassPathFileHandleResolver());

        AssetManager.installAssets(assetsFile);
        SceneManager.loadSceneData(scenesFile);
    }

    @Override
    public void resize(int width, int height) {
        Scene scene = SceneManager.getCurrentScene();
        if (scene != null) {
            Stage stage = scene.getStage();
            Viewport viewport = stage.getViewport();
            viewport.update(width, height);
        }
    }

    @Override
    public void render() {
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        Scene scene = SceneManager.getCurrentScene();
        if (scene != null) {
            Stage stage = scene.getStage();
            stage.act(dt);
            stage.draw();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        AssetManager.cleanup();
        SceneManager.cleanup();
    }
}
