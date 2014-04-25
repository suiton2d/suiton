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
import com.nebula2d.editor.framework.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewProjectDialog extends JDialog {

    private JTextField projNameTf;
    private JTextField parentDirTf;
    private JButton browseBtn;
    private JButton createBtn;
    private JButton cancelBtn;

    public NewProjectDialog() {
        setTitle("New Project");

        JLabel projNameLbl = new JLabel("Project Name:");
        JLabel parentDirLbl = new JLabel("Parent Directory:");

        projNameTf = new JTextField(20);
        parentDirTf = new JTextField(20);

        browseBtn = new JButton("...");
        createBtn = new JButton("Create");
        cancelBtn = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);


        JPanel mainPanel = new JPanel();
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(projNameLbl).addComponent(parentDirLbl));
        hGroup.addGroup(layout.createParallelGroup().addComponent(projNameTf).addComponent(parentDirTf));
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(projNameLbl).addComponent(projNameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                addComponent(parentDirLbl).addComponent(parentDirTf).addComponent(browseBtn));
        layout.setVerticalGroup(vGroup);

        add(mainPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setModal(true);
        bindButtons();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void bindButtons() {
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!validateText()) {
                    JOptionPane.showMessageDialog(NewProjectDialog.this,
                            "You must provide a valid project name and parent directory.");

                    return;
                }
                Project project = new Project(parentDirTf.getText().trim(), projNameTf.getText().trim());
                project.addScene(new Scene("Untitled Scene 0"));
                MainFrame.setProject(project);
                dispose();
            }
        });

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a Directory");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    parentDirTf.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private boolean validateText() {
        if (projNameTf.getText().trim().equals("") || parentDirTf.getText().trim().equals("")) {
            return false;
        }

        File parentDir = new File(parentDirTf.getText().trim());

        return parentDir.exists() && parentDir.isDirectory();
    }
}
