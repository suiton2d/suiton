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

package com.suiton2d.components;

import com.badlogic.gdx.utils.Array;
import com.suiton2d.scene.GameObject;

/**
 * AnimatedRenderer is an abstract base class for rendering components
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public abstract class AnimatedRenderer<T extends Animation> extends Component implements Renderer {

    protected Array<T> animations = new Array<>();
    protected T currentAnimation;

    public AnimatedRenderer() {}

    public AnimatedRenderer(String name) {
        super(name);
    }

    public Array<T> getAnimations() {
        return animations;
    }

    @SuppressWarnings("unused")
    public T getCurrentAnimation() {
        return currentAnimation;
    }

    public int getCurrentAnimationIndex() {
        return currentAnimation != null ? animations.indexOf(currentAnimation, true) : -1;
    }

    @SuppressWarnings("unused")
    public void setCurrentAnimation(T currentAnimation) {
        if (animations.contains(currentAnimation, true))
            this.currentAnimation = currentAnimation;
    }

    public void setCurrentAnimation(int idx) {
        if (animations.size > idx)
            this.currentAnimation = animations.get(idx);
    }

    /**
     * Finds the {@link Animation} attached to the AnimatedRenderer with the given name.
     * @param name The name of the animation to find.
     * @return The target animation if it exists, else null.
     */
    @SuppressWarnings("unused")
    public T getAnimation(String name) {
        for (T anim : animations) {
            if (anim.getName().equals(name)) {
                return anim;
            }
        }

        return null;
    }

    public void addAnimation(T anim) {
        animations.add(anim);
    }

    @SuppressWarnings("unused")
    public void removeAnimation(T anim) {
        animations.removeValue(anim, true);
    }

    /**
     * Sets the animation that will be used during rendering.
     * @param name The name of the animation to set as active. Can be null. If an Animation with
     *             the given name cannot be found, the current animation will not be updated.
     */
    @SuppressWarnings("unused")
    public void setCurrentAnimation(String name) {

        if (name == null) {
            currentAnimation = null;
            return;
        }

        for (T anim : animations) {
            if (anim.getName().equals(name))
                currentAnimation = anim;
        }
    }



    public void finish() {

    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }
}
