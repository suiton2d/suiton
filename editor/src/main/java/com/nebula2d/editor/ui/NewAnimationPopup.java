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

import com.nebula2d.editor.framework.assets.AssetManager;
import com.nebula2d.editor.framework.assets.Sprite;
import com.nebula2d.editor.framework.components.Animation;
import com.nebula2d.editor.framework.components.KeyFrameAnimation;
import com.nebula2d.editor.framework.components.Renderer;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewAnimationPopup extends JPopupMenu {
    private Renderer renderer;
    private DefaultListModel<Animation> listModel;
    private Sprite sprite;

    public NewAnimationPopup(Renderer renderer, DefaultListModel<Animation> listModel, final String texturePath) {
        this.renderer = renderer;
        this.listModel = listModel;
        int currScene = MainFrame.getProject().getCurrentScene().getId();
        sprite = AssetManager.getInstance().getOrCreateSprite(currScene, texturePath);
        JMenuItem kfAnimMenuItem = add("KeyFrameAnimation");
        kfAnimMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyFrameAnimation kfAnim = new KeyFrameAnimation("", sprite);
                new NewAnimationDialog(kfAnim);
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });
    }

    private class NewAnimationDialog extends JDialog {
        private Animation animation;

        public NewAnimationDialog(Animation animation) {
            this.animation = animation;
            final N2DLabel errorMsg = new N2DLabel("You must enter a valid name for the animation.");
            errorMsg.setForeground(Color.RED);
            errorMsg.setVisible(false);
            final N2DLabel nameLbl = new N2DLabel("Name:");
            final JTextField nameTf = new JTextField(20);
            final N2DPanel namePanel = new N2DPanel();
            namePanel.add(nameLbl);
            namePanel.add(nameTf);

            final JButton okBtn = new JButton("Ok");
            okBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameTf.getText();
                    if (!validateText(name)) {
                        errorMsg.setVisible(true);
                        return;
                    }

                    errorMsg.setVisible(false);
                    NewAnimationDialog.this.animation.setName(name);
                    NewAnimationDialog.this.animation.init();
                    renderer.addAnimation(NewAnimationDialog.this.animation);
                    listModel.addElement(NewAnimationDialog.this.animation);
                    dispose();
                }
            });
            final JButton cancelBtn = new JButton("Cancel");
            cancelBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            final N2DPanel buttonPanel = new N2DPanel();
            buttonPanel.add(okBtn);
            buttonPanel.add(cancelBtn);

            add(errorMsg, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);
            add(namePanel);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private boolean validateText(String text) {
            return !text.trim().equals("");
        }
    }
}
