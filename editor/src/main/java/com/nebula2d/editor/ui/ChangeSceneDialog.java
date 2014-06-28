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

package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.Project;
import com.nebula2d.editor.framework.Scene;
import com.nebula2d.editor.ui.controls.N2DList;
import com.nebula2d.editor.ui.controls.N2DPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class ChangeSceneDialog extends JDialog {

    public ChangeSceneDialog() {
        setTitle("Change Scene");
        setupContents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupContents() {
        final DefaultListModel<Scene> model = createListModel();
        final N2DList<Scene> sceneListBox = new N2DList<>(model);
        final JButton okBtn = new JButton("Ok");
        final JButton cancelBtn = new JButton("Cancel");

        N2DPanel btnPanel = new N2DPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        sceneListBox.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                okBtn.setEnabled(!sceneListBox.isSelectionEmpty());
            }
        });

        okBtn.addActionListener(e -> {
            Scene scene = sceneListBox.getSelectedValue();
            Project project = MainFrame.getProject();
            SceneGraph sceneGraph = MainFrame.getSceneGraph();
            project.setCurrentScene(scene.getName());
            sceneGraph.init();
            project.loadCurrentScene();
            sceneGraph.refresh();
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        add(btnPanel, BorderLayout.SOUTH);
        add(new JScrollPane(sceneListBox), BorderLayout.CENTER);

        setMinimumSize(new Dimension(300, 100));
        pack();
    }

    private DefaultListModel<Scene> createListModel() {
        Project project = MainFrame.getProject();
        DefaultListModel<Scene> sceneList = new DefaultListModel<>();
        for (Scene scene : project.getScenes()) {
            sceneList.addElement(scene);
        }

        return sceneList;
    }
}
