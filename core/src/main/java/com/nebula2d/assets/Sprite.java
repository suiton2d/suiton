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

package com.nebula2d.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Sprite is a wrapper class around libGdx's Texture class
 * for 2D textures
 */
public class Sprite extends Asset {

    protected Texture texture;

    public Sprite(String path) {
        super(path);
    }

    public Texture getTexture() {
        return texture;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    @Override
    protected void onLoad() {
        texture = new Texture(Gdx.files.internal(path));
    }

    @Override
    protected void onUnload() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }
}
