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

package com.suiton2d.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.editor.framework.Project;
import com.suiton2d.editor.settings.N2DSettings;
import com.suiton2d.editor.ui.assets.AssetsPane;
import com.suiton2d.editor.ui.render.RenderCanvas;
import com.suiton2d.editor.ui.scene.SceneGraph;
import com.suiton2d.scene.SceneData;
import com.suiton2d.scene.SceneManager;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame {
    private RenderCanvas renderCanvas;
    private SceneGraph sceneGraph;
    private SuitonToolbar toolbar = new SuitonToolbar();
    private SuitonMenuBar menuBar;
    private N2DSettings settings = new N2DSettings();
    private AssetsPane assetsPane = new AssetsPane();
    private AssetManager assetManager;
    private SceneManager sceneManager;
    private Project project;

    public MainFrame() {
        super("Nebula2D");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle windowSize = ge.getMaximumWindowBounds();
        assetManager = new AssetManager(new AbsoluteFileHandleResolver());
        sceneManager = new SceneManager(assetManager, new SceneData(""));
        World world = new World(new Vector2(0, 0), true);
        renderCanvas = new RenderCanvas(this, sceneManager);

        try {
            settings.loadFromProperties();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load configuration.");
            System.exit(1);
        }
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel canvasPanel = new JPanel(new BorderLayout());
        canvasPanel.add(renderCanvas.getCanvas());

        sceneGraph = new SceneGraph(this);
        sceneGraph.setEnabled(false);
        menuBar = new SuitonMenuBar(this, sceneManager, assetManager, world);
        setJMenuBar(menuBar);
        JScrollPane sp = new JScrollPane(sceneGraph);
        sp.setPreferredSize(new Dimension(windowSize.width/6, (windowSize.height - windowSize.height/4)));
        JSplitPane hSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, canvasPanel);

        JSplitPane vSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, hSplitPane, assetsPane);
        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(vSplitPane);
        pack();
        setVisible(true);
        vSplitPane.setDividerLocation(getHeight() - getHeight()/3);

        setSize(new Dimension(windowSize.width, windowSize.height));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Are you sure?",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        settings.saveProperties();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Failed to save settings.");
                    }
                    assetManager.cleanup();

                    Gdx.app.postRunnable(() -> {
                        renderCanvas.stop();
                        SwingUtilities.invokeLater(MainFrame.this::dispose);
                    });
                }
            }
        });
    }

    public void setProject(Project project) {
        this.project = project;
        sceneGraph.wipe();
        sceneGraph.init(sceneManager);
        sceneGraph.setEnabled(project != null);
        menuBar.setProject(project);
        toolbar.setRendererWidgetsEnabled(project != null);
        if (project != null) {
            setTitle("Nebula2D - " + project.getName());
            if (!assetsPane.getAssetsTree().isInitialized())
                assetsPane.getAssetsTree().init(project);
        } else {
            setTitle("Nebual2D");
        }
    }

    public N2DSettings getSettings() {
        return settings;
    }

    public SuitonMenuBar getSuitonMenuBar() {
        return menuBar;
    }

    public SuitonToolbar getSuitonToolbar() {
        return toolbar;
    }

    public SceneGraph getSceneGraph() {
        return sceneGraph;
    }

    public RenderCanvas getRenderCanvas() {
        return renderCanvas;
    }

    public Project getProject() {
        return project;
    }
}
