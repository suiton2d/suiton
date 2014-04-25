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

package com.nebula2d.scene;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nebula2d.assets.AssetManager;

import java.util.HashMap;
import java.util.Map;

/**
 * SceneManager is a singleton class used to manage the game's various {@link Scene}s.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class SceneManager {

    private static SceneManager instance;
    private Scene currentScene;
    private Map<String, Scene> sceneMap;

    private SceneManager() {
        sceneMap = new HashMap<String, Scene>();
    }

    /**
     * Retrieves the singleton instance of SceneManager. SceneManager should
     * only ever have once instance at any given time.
     * @return Singleton SceneManager instance.
     */
    public static synchronized SceneManager getInstance() {
        if (instance == null)
            instance = new SceneManager();

        return instance;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Retrieves the {@link Scene} with the given name.
     * @param name The name of the Scene to retrieve.
     * @return The target Scene.
     */
    public Scene getScene(String name) {
        return sceneMap.get(name);
    }

    /**
     * Adds a Scene to the scene manager.
     * @param scene The Scene to add.
     */
    public void addScene(Scene scene) {
        sceneMap.put(scene.getName(), scene);
    }

    /**
     * Changes the current scene, unloading the currently loaded
     * assets and loading in the assets for the new scene.
     * @param name The name of the Scene to load.
     */
    public void setCurrentScene(String name) {

        if (sceneMap.containsKey(name)) {
            if (currentScene != null) {
                currentScene.finish();
                AssetManager.getInstance().unloadAssets(currentScene.getName());
            }
            currentScene = getScene(name);
            AssetManager.getInstance().loadAssets(name);
            currentScene.start();
        }
    }

    public void start() {

        currentScene.start();
    }

    public void update(float dt) {
        currentScene.update(dt);
    }

    public void fixedUpdate() {
        World physicalWorld = currentScene.getPhysicalWorld();
        physicalWorld.step(1/45f, 6, 2);

        Array<Body> bodies = new Array<Body>();
        physicalWorld.getBodies(bodies);

        for (Body body : bodies) {
            if (body.getType() != BodyDef.BodyType.StaticBody) {
                GameObject go = (GameObject) body.getUserData();
                go.getTransform().setPosition(body.getPosition());
                go.getTransform().setRotation((float) (body.getAngle() * 180.0f / Math.PI));
            }
        }
    }
}
