package com.nebula2d.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
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

    private SceneManager sceneManager = SceneManager.getInstance();
    private AssetManager assetManager = AssetManager.getInstance();

    @Override
    public void create() {
        FileHandle assetsFile = Gdx.files.internal("assets.xml");
        FileHandle scenesFile = Gdx.files.internal("scenes.xml");

        assetManager.installAssets(assetsFile);
        sceneManager.loadSceneData(scenesFile);

//        sceneManager.setCurrentScene(sceneManager.getStartScene());
        sceneManager.addScene(new Scene("test", new Vector2(), true));
        sceneManager.setCurrentScene("test");
    }

    @Override
    public void resize(int width, int height) {
        Scene scene = sceneManager.getCurrentScene();
        Stage stage = scene.getStage();
        Viewport viewport = stage.getViewport();
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        Stage stage = sceneManager.getCurrentScene().getStage();
        stage.act(dt);
        stage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        assetManager.cleanup();
        sceneManager.cleanup();

        assetManager = null;
        sceneManager = null;
    }
}
