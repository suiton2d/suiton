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

package com.suiton2d.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.suiton2d.util.CollisionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene is a class representing a single "level" in the game.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Scene {

    private String name;
    private List<Layer> layers;
    private Stage stage;
    private World physicalWorld;
    private boolean allowSleeping;

    public Scene(String name, Vector2 gravity, boolean sleepPhysics) {
        physicalWorld = new World(gravity, sleepPhysics);
        physicalWorld.setContactListener(new CollisionListener(this));
        stage = new Stage(new ScreenViewport());
        this.name = name;
        this.allowSleeping = sleepPhysics;
        this.layers = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Stage getStage() {
        return stage;
    }

    public Camera getCamera() {
        return stage.getCamera();
    }

    public World getPhysicalWorld() {
        return physicalWorld;
    }

    public Vector2 getGravity() {
        return physicalWorld.getGravity();
    }

    public void setGravity(int x, int y) {
        physicalWorld.setGravity(new Vector2(x, y));
    }

    public boolean isAllowSleeping() {
        return allowSleeping;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void addLayer(Layer l) {
        layers.add(l);
        l.setScene(this);
    }

    public void removeLayer(Layer l) {
        layers.remove(l);
        l.setScene(null);
    }

    /**
     * Retrieves the {@link Layer} in the Scene with the given name.
     * @param name The name of the Layer to retrieve.
     * @return the target Layer.
     */
    public Layer getLayer(String name) {
        for (Layer l : layers) {
            if (l.getName().equals(name))
                return l;
        }

        return null;
    }

    public void start() {
        for (Layer l : layers)
            l.start(stage);
    }

    public void update(float dt) {
        Camera cam = stage.getCamera();

        if (cam == null)
            return;

        cam.update();
        stage.act(dt);
        stage.draw();
    }

    public void finish() {
        for (Layer layer : layers)
            layer.finish();

        if (stage != null)
            stage.dispose();

        if (physicalWorld != null)
            physicalWorld.dispose();
    }

    public void beginCollision(GameObject go1, GameObject go2) {
        for (Layer layer : layers)
            layer.beginCollision(go1, go2);
    }

    public void endCollision(GameObject go1, GameObject go2) {
        for (Layer layer : layers)
            layer.endCollision(go1, go2);
    }

    public void cleanup() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (physicalWorld != null) {
                    physicalWorld.dispose();
                    physicalWorld = null;
                }

                if (stage != null) {
                    stage.dispose();
                    stage = null;
                }
            }
        });
    }
}
