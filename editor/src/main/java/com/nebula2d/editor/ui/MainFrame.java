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
import com.nebula2d.editor.framework.Project;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    //region static members
    private static RenderCanvas renderCanvas = new RenderCanvas(new RenderAdapter());
    private static SceneGraph sceneGraph = new SceneGraph();
    private static N2DToolbar toolbar = new N2DToolbar();
    private static N2DMenuBar menuBar;
    private static Project project;
    //endregion

    //region constructor
    public MainFrame() {
        super("Nebula2D");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JScrollPane sp = new JScrollPane(sceneGraph);
        sp.setPreferredSize(new Dimension(300, 600));

        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(sp, BorderLayout.WEST);
        getContentPane().add(renderCanvas.getCanvas());

        sceneGraph.setEnabled(false);
        renderCanvas.setEnabled(false);

        menuBar = new N2DMenuBar();
        setJMenuBar(menuBar);

        setSize(1200, 768);

        validate();

        setVisible(true);
        renderCanvas.initCamera(renderCanvas.getCanvas().getWidth(), renderCanvas.getCanvas().getHeight());
    }
    //endregion

    //region static accessors
    public static RenderCanvas getRenderCanvas() {
        return renderCanvas;
    }

    public static SceneGraph getSceneGraph() {
        return sceneGraph;
    }

    public static Project getProject() {
        return project;
    }

    public static N2DMenuBar getN2DMenuBar() {
        return menuBar;
    }

    public static N2DToolbar getToolbar() {
        return toolbar;
    }

    public static void setProject(Project project) {
        MainFrame.project = project;
        sceneGraph.setEnabled(true);
        renderCanvas.setEnabled(true);
        menuBar.getSceneMenu().setEnabled(true);
        toolbar.setRendererWidgetsEnabled(true);
        sceneGraph.init();
    }
    //endregion
}
