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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.components.Animation;

import javax.swing.*;
import java.awt.*;

public class AnimationRenderAdapter implements ApplicationListener {

    private Animation animation;
    private OrthographicCamera camera;
    private int w, h;

    public AnimationRenderAdapter(Animation animation) {
        this.animation = animation;
    }

    public void initCamera(int w, int h) {
        this.w = w;
        this.h = h;
        camera = new OrthographicCamera(w, h);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i2) {

    }

    @Override
    public void render() {
        Color backgroundColor = UIManager.getColor("Panel.background");
        Gdx.graphics.getGL20().glClearColor(backgroundColor.getRed()/255.0f,
                backgroundColor.getGreen()/255.0f, backgroundColor.getBlue()/255.0f, 1.0f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (camera == null)
            return;

        camera.update();
        SpriteBatch badger = new SpriteBatch();

        badger.begin();
        animation.renderAnimated(badger, camera, w, h);
        badger.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public Animation getAnimation() {
        return animation;
    }
}
