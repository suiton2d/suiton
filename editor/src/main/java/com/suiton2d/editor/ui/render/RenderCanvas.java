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

package com.suiton2d.editor.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.suiton2d.editor.framework.Selection;
import com.suiton2d.scene.GameObject;

public class RenderCanvas extends LwjglCanvas {

    protected RenderAdapter adapter;

    protected boolean isMouseDown;

    public RenderCanvas(RenderAdapter adapter) {
        super(adapter);
        this.adapter = adapter;
        this.isMouseDown = false;
        Gdx.input.setInputProcessor(new CanvasInputHandler(this));
    }

    public Selection getSelectedObject() {
        return adapter.getSelectedObject();
    }

    public void setSelectedObject(GameObject go) {
        this.adapter.setSelectedObject(new Selection(go));
    }
}
