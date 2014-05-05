/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
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

package com.nebula2d.editor.common;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;

/**
 * Interface for rendering.
 *
 * Created by bonazza on 5/3/14.
 */
public interface IRenderable {

    /**
     * Renders the object.
     * @param selectedObject the currently selected {@link com.nebula2d.editor.framework.GameObject}. Null if no object
     * is selected.
     * @param batcher The SpriteBatch to use for rendering.
     * @param cam The Camera to use for rendering.
     */
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam);
}
