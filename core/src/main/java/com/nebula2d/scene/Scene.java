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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nebula2d.util.CollisionListener;

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

    public Scene(String name, Vector2 gravity, boolean sleepPhysics) {
        physicalWorld = new World(gravity, sleepPhysics);
        physicalWorld.setContactListener(new CollisionListener(this));
        stage = new Stage(new ScreenViewport());
        this.name = name;
        this.layers = new ArrayList<Layer>();
    }

    public String getName() {
        return name;
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

    public List<Layer> getLayers() {
        return layers;
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

    /**
     * Removes a Layer from the Scene.
     * @param layer The Layer to remove.
     */
    public void remove(Layer layer) {
        layers.remove(layer);
        layer.setScene(null);
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
        if (physicalWorld != null) {
            physicalWorld.dispose();
            physicalWorld = null;
        }

        if (stage != null) {
            stage.dispose();
            stage = null;
        }
    }
}
