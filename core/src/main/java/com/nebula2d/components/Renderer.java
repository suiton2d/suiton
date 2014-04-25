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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.nebula2d.scene.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Renderer is an abstract base class for rendering components
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public abstract class Renderer extends Component {

    protected List<Animation> animations;
    protected Animation currentAnimation;

    public Renderer(String name) {
        super(name);
        animations = new ArrayList<Animation>();
    }

    /**
     * Finds the {@link com.nebula2d.components.Animation} attached to the Renderer with the given name.
     * @param name The name of the animation to find.
     * @return The target animation if it exists, else null.
     */
    public Animation getAnimation(String name) {
        for (Animation anim : animations) {
            if (anim.getName().equals(name)) {
                return anim;
            }
        }

        return null;
    }

    public void addAnimation(Animation anim) {
        animations.add(anim);
    }

    public void removeAnimation(Animation anim) {
        animations.remove(anim);
    }

    /**
     * Sets the animation that will be used during rendering.
     * @param name The name of the animation to set as active. Can be null. If an Animation with
     *             the given name cannot be found, the current animation will not be updated.
     */
    public void setCurrentAnimation(String name) {

        if (name == null) {
            currentAnimation = null;
            return;
        }

        for (Animation anim : animations) {
            if (anim.getName().equals(name))
                currentAnimation = anim;
        }
    }

    /**
     * Abstract method used for rendering.
     * @param batch The SpriteBatch instance to use for rendering.
     * @param dt The time since the last frame update.
     */
    public abstract void render(Batch batch, float dt);

    public void finish() {

    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }
}
