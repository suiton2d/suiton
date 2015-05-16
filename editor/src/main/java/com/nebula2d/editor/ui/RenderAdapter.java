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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nebula2d.editor.framework.Project;
import com.nebula2d.editor.framework.Selection;
import com.nebula2d.scene.Scene;
import com.nebula2d.scene.SceneManager;


public class RenderAdapter implements ApplicationListener {

    private Selection selectedObject;

    public RenderAdapter() {

    }

    public Selection getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(Selection selectedObject) {
        this.selectedObject = selectedObject;
    }

    @Override
    public void create() {

    }

    public OrthographicCamera getCamera() {
        return (OrthographicCamera) MainFrame.getProject().getCurrentScene().getCamera();
    }

    @Override
    public void resize(int width, int height) {
        Scene scene = SceneManager.getCurrentScene();
        if (scene != null) {
            Stage stage = scene.getStage();
            Viewport viewport = stage.getViewport();
            viewport.update(width, height);
        }
    }

    @Override
    public void render() {
        Gdx.graphics.getGL20().glClearColor(.17f, .17f, .17f, 1.0f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        Project p = MainFrame.getProject();
        if (p != null && p.getCurrentScene() != null) {
            Scene scene = p.getCurrentScene();
            scene.update(Gdx.graphics.getDeltaTime());
            if (selectedObject != null)
                selectedObject.renderSelection(scene.getCamera());
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (MainFrame.getProject() != null && MainFrame.getProject().getCurrentScene() != null)
            MainFrame.getProject().getCurrentScene().cleanup();
    }
}
