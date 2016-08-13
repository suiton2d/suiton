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
import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.editor.framework.Project;
import com.suiton2d.editor.framework.SceneNode;
import com.suiton2d.editor.io.loaders.ProjectSceneDataLoader;
import com.suiton2d.editor.ui.scene.ChangeSceneDialog;
import com.suiton2d.editor.ui.scene.NewSceneDialog;
import com.suiton2d.editor.ui.scene.RenameSceneDialog;
import com.suiton2d.editor.util.ExitAction;
import com.suiton2d.editor.util.PlatformUtil;
import com.suiton2d.editor.util.ProjectBuilder;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.SceneData;
import com.suiton2d.scene.SceneManager;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;


/**
 * Custom JMenuBar implementation
 */
public class SuitonMenuBar extends JMenuBar {

    private MainFrame mainFrame;
    private Project project;

    private JMenu sceneMenu;
    private JMenu gameObjectMenu;

    private JMenuItem newMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem settingsMenuItem;
    private JMenuItem buildMenuItem;

    private JMenuItem newSceneMenuItem;
    private JMenuItem changeSceneMenuItem;
    private JMenuItem renameSceneMenuItem;
    private JMenuItem newLayerMenuItem;

    private JMenuItem newEmptyGameObjectMenuItem;

    public SuitonMenuBar(MainFrame mainFrame, SceneManager sceneManager, AssetManager assetManager, World world) {
        this.mainFrame = mainFrame;
        JMenu fileMenu = new JMenu("File");

        newMenuItem = fileMenu.add("New Project");
        saveMenuItem = fileMenu.add("Save");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
        saveMenuItem.setEnabled(false);
        openMenuItem = fileMenu.add("Open Project");
        settingsMenuItem = fileMenu.add("Settings");
        buildMenuItem = fileMenu.add("Build");

        setBorder(BorderFactory.createEmptyBorder());
        //Don't need exit menu item on Mac
        if (!PlatformUtil.isMac())
            fileMenu.add(new ExitAction());

        sceneMenu = new JMenu("Scene");

        newSceneMenuItem = sceneMenu.add("New Scene");
        changeSceneMenuItem = sceneMenu.add("Change Scene");
        renameSceneMenuItem = sceneMenu.add("Rename Scene");
        newLayerMenuItem = sceneMenu.add("New Layer");

        gameObjectMenu = new JMenu("New GameObject");

        newEmptyGameObjectMenuItem = gameObjectMenu.add("Empty GameObject");

        sceneMenu.add(gameObjectMenu);

        buildMenuItem.setEnabled(false);
        gameObjectMenu.setEnabled(false);
        sceneMenu.setEnabled(false);
        add(fileMenu);
        add(sceneMenu);
        bindMenuItems(sceneManager, assetManager, world);
    }

    public void setProject(Project project) {
        this.project = project;
        buildMenuItem.setEnabled(project != null);
        sceneMenu.setEnabled(project != null);
        saveMenuItem.setEnabled(project != null);
    }

    @SuppressWarnings("unchecked")
    private void bindMenuItems(SceneManager sceneManager, AssetManager assetManager, World world) {
        newMenuItem.addActionListener(e -> new NewProjectDialog(mainFrame, sceneManager, assetManager));

        saveMenuItem.addActionListener(e -> {
            try {
                project.saveProject();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Failed to save project.");
            }
        });

        openMenuItem.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Nebula2D Project File (*.n2d)", "n2d"));
            fc.setAcceptAllFileFilterUsed(false);
            if (fc.showOpenDialog(SuitonMenuBar.this) == JFileChooser.APPROVE_OPTION) {
                mainFrame.getSceneGraph().wipe();
                Project newProject = new Project(fc.getSelectedFile().getAbsolutePath(), sceneManager);
                mainFrame.setProject(newProject);

                Gdx.app.postRunnable(() -> {
                    try {
                        ProjectSceneDataLoader loader = new ProjectSceneDataLoader(assetManager, world);
                        SceneData sceneData = loader.loadSceneData(assetManager, Gdx.files.absolute(project.getPath()));
                        sceneManager.clear();
                        sceneManager.init(sceneData);
                        mainFrame.getSceneGraph().init(sceneManager);
                        SwingUtilities.invokeLater(() -> {
                            sceneManager.getCurrentScene().getChildren().forEach((l) -> mainFrame.getSceneGraph().addLayer((Layer) l));
                            mainFrame.getSceneGraph().refresh();
                        });
                    } catch (IOException e1) {
                        mainFrame.setProject(null);
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(SuitonMenuBar.this, e1.getMessage()));
                    }
                });

            }
        });

        settingsMenuItem.addActionListener(e -> new SettingsDialog(mainFrame.getSettings()));

        buildMenuItem.addActionListener(e -> {
            try {
                // TODO: Make start scene configurable.
                new ProjectBuilder(project).build(sceneManager.getCurrentScene().getName(), ProjectBuilder.ProjectType.PC);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Failed to build project.");
            }
        });

        newSceneMenuItem.addActionListener(e -> new NewSceneDialog(mainFrame, sceneManager));

        changeSceneMenuItem.addActionListener(e -> new ChangeSceneDialog(mainFrame, sceneManager));
        renameSceneMenuItem.addActionListener(e -> new RenameSceneDialog(mainFrame, sceneManager));
        newLayerMenuItem.addActionListener(e -> {
            Layer layer = new Layer("New Layer " + mainFrame.getSceneGraph().getLayerCount(),
                    sceneManager.getCurrentScene().getChildren().size);
            sceneManager.getCurrentScene().addLayer(layer);
            mainFrame.getSceneGraph().addLayer(layer);
        });

        newEmptyGameObjectMenuItem.addActionListener(e -> {
            //If this menu item is enabled, we know 100% that something is selected, so no check is necessary. =)
            SceneNode selectedNode = (SceneNode) mainFrame.getSceneGraph().getLastSelectedPathComponent();

            GameObject go = new GameObject("Empty Game Object " + mainFrame.getSceneGraph().getGameObjectCount());
            selectedNode.add(go.getName(), go);
            mainFrame.getSceneGraph().refresh();
        });
    }

    public JMenu getGameObjectMenu() {
        return gameObjectMenu;
    }
}
