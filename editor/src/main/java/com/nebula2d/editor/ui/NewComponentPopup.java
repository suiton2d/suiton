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


import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.components.*;
import com.nebula2d.editor.framework.components.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewComponentPopup extends JPopupMenu {

    private GameObject gameObject;
    private DefaultListModel<Component> listModel;
    private JList<Component> list;

    public NewComponentPopup(GameObject gameObject, JList<Component> list) {
        this.list = list;
        this.gameObject = gameObject;
        this.listModel = (DefaultListModel<Component>) list.getModel();

        create();
    }

    private void create() {
        JMenu rendererMenu = new JMenu("Renderer");
        JMenuItem panelRendererMenuItem = rendererMenu.add("SpriteRenderer");

        JMenu audioMenu = new JMenu("Audio");
        JMenuItem musicSourceMenuItem = audioMenu.add("MusicSource");
        JMenuItem soundEffectSourceMenuItem = audioMenu.add("SoundEffectSource");

        JMenuItem behaviorMenuItem = new JMenuItem("Behavior");

        behaviorMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Behaviour behaviour = new Behaviour();
                new NewComponentDialog(behaviour);
            }
        });

        musicSourceMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicSource musicSource = new MusicSource("");
                new NewComponentDialog(musicSource);
            }
        });

        soundEffectSourceMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffectSource soundEffectSource = new SoundEffectSource("");
                new NewComponentDialog(soundEffectSource);
            }
        });

        panelRendererMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (NewComponentPopup.this.gameObject.getRenderer() != null) {
                    JOptionPane.showMessageDialog(NewComponentPopup.this, "This GameObject already has a renderer attached.");
                    return;
                }

                new NewComponentDialog(new SpriteRenderer(""));
            }
        });

        add(rendererMenu);
        add(audioMenu);
        add(behaviorMenuItem);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });
    }

    private class NewComponentDialog extends JDialog {
        private Component component;

        public NewComponentDialog(Component component) {
            this.component = component;
            final JLabel errorMessage = new JLabel("You must enter a valid name for the component.");
            errorMessage.setForeground(Color.red);
            errorMessage.setVisible(false);

            final JLabel nameLbl = new JLabel("Name:");
            final JTextField nameTf = new JTextField(20);
            JPanel namePanel = new JPanel();
            namePanel.add(nameLbl);
            namePanel.add(nameTf);

            JButton okBtn = new JButton("Ok");
            okBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameTf.getText();
                    if (!validateText(name)) {
                        errorMessage.setVisible(true);
                        return;
                    }

                    errorMessage.setVisible(false);
                    NewComponentDialog.this.component.setName(name);
                    gameObject.addComponent(NewComponentDialog.this.component);

                    listModel.addElement(NewComponentDialog.this.component);
                    dispose();
                    list.setSelectedValue(NewComponentDialog.this.component, true);


                }
            });
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(okBtn);
            buttonPanel.add(cancelBtn);

            add(errorMessage, BorderLayout.NORTH);
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
