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

import com.badlogic.gdx.scenes.scene2d.Stage;

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

    public Scene(String name, int index) {
        this.name = name;
        this.layers = new ArrayList<Layer>();
        this.stage = new Stage();
    }

    //region accessors
    public String getName() {
        return name;
    }

    public List<Layer> getLayers() {
        return layers;
    }
    //endregion

    //region layer ops

    /**
     * Retreives the {@link Layer} in the Scene with the given name
     * @param name the name of the Layer to retrieve
     * @return the target Layer
     */
    public Layer getLayer(String name) {
        for (Layer l : layers) {
            if (l.getName().equals(name))
                return l;
        }

        return null;
    }



    /**
     * Removes a Layer from the Scene
     * @param layer the Layer to remove
     */
    public void remove(Layer layer) {
        layers.remove(layer);
        layer.setScene(null);
    }
    //endregion

    public void start() {
        for (Layer l : layers)
            l.start(stage);
    }

    public void update(float dt) {
        for (Layer l : layers)
            l.update(dt);
    }
}
