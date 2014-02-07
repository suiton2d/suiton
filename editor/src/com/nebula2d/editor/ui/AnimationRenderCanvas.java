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

package com.nebula2d.editor.ui;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.nebula2d.editor.framework.assets.Texture;

import java.awt.*;

public class AnimationRenderCanvas extends LwjglAWTCanvas {

    private AnimationRenderAdapter adapter;

    public AnimationRenderCanvas(AnimationRenderAdapter adapter) {
        super(adapter, true, MainFrame.getRenderCanvas());
        this.adapter = adapter;
        Texture tex = adapter.getAnimation().getTexture();
        getCanvas().setPreferredSize(new Dimension(tex.getWidth(), tex.getHeight()));
    }

    public void initCamera() {
        adapter.initCamera(getCanvas().getWidth(), getCanvas().getHeight());
    }
}
