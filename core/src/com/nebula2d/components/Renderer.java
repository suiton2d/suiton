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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public abstract void render(SpriteBatch batch, Camera cam, float dt);
}
