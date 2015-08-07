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

package com.suiton2d.editor.ui.scene;

import com.suiton2d.editor.ui.MainFrame;
import com.suiton2d.editor.ui.controls.SuitonList;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneManager;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class ChangeSceneDialog extends JDialog {

    public ChangeSceneDialog() {
        setTitle("Change Scene");
        setupContents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupContents() {
        final DefaultListModel<Scene> model = createListModel();
        final SuitonList<Scene> sceneListBox = new SuitonList<>(model);
        final JButton okBtn = new JButton("Ok");
        final JButton cancelBtn = new JButton("Cancel");

        JPanel btnPanel = new JPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        sceneListBox.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                okBtn.setEnabled(!sceneListBox.isSelectionEmpty());
            }
        });

        okBtn.addActionListener(e -> {
            Scene scene = sceneListBox.getSelectedValue();
            SceneGraph sceneGraph = MainFrame.getSceneGraph();
            SceneManager.setCurrentScene(scene.getName());
            sceneGraph.init();
            scene.getLayers().forEach(sceneGraph::addLayer);
            sceneGraph.refresh();
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
        DefaultListModel<Scene> sceneList = new DefaultListModel<>();
        for (Scene scene : SceneManager.getSceneList())
            sceneList.addElement(scene);

        return sceneList;
    }
}
