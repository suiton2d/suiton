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

package com.nebula2d.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.scene.Transform;

/**
 * Animation is an abstract base class for animations.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com></jonbonazza@gmail.com>
 */
public abstract class Animation {

    protected String name;
    protected boolean isPlaying;
    protected boolean isPaused;

    public Animation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Plays the animation.
     */
    public void play() {
        isPlaying = true;
        isPaused = false;
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        isPlaying = false;
        isPaused = false;
    }

    /**
     * Pauses the animation.
     */
    public void pause() {
        if (isPlaying)
            isPaused = true;
    }

    protected abstract TextureRegion update(float dt);

    /**
     * Abstract method to render the Animation
     * @param transform The {@link com.nebula2d.scene.Transform} of the associated {@link com.nebula2d.scene.GameObject}.
     * @param batch The SpriteBatch instance to use for rendering
     * @param cam The Camera for the current scene.
     * @param dt The time since the last frame update.
     */
    public abstract void render(Transform transform, Batch batch, Camera cam, float dt);
}
