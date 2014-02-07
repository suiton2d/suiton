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

package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.ImagePanel;
import com.nebula2d.editor.ui.NewAnimationPopup;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelRenderer extends Renderer {

    //region constructor
    public PanelRenderer(String name) {
        super(name);
    }
    //endregion

    //region overrided methods from Renderer
    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        if (texture != null) {

            if (currentAnim != -1) {
                getCurrentAnimation().renderStill(batcher, parent, cam);
                return;
            }
            float halfw = getBoundingWidth() / 2.0f;
            float halfh = getBoundingHeight() / 2.0f;

            batcher.draw(new TextureRegion(texture.getTexture()),
                    parent.getPosition().x - halfw - cam.position.x,
                    parent.getPosition().y - halfh - cam.position.y,
                    halfw,
                    halfh,
                    texture.getTexture().getWidth(),
                    texture.getTexture().getHeight(),
                    parent.getScale().x,
                    parent.getScale().y,
                    parent.getRotation());
        }
    }

    @Override
    public JPanel forgeComponentContentPanel(final ComponentsDialog parent) {

        final ImagePanel imagePanel = new ImagePanel();

        final JButton addButton = new JButton("Add");
        addButton.setEnabled(false);
        final JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        final JTextField imageTf = new JTextField(20);

        if (texture != null) {
            imageTf.setText(texture.getPath());
            imagePanel.setImage(getTexture().getPath());
        }

        final DefaultListModel<Animation> listModel = new DefaultListModel<Animation>();
        for (Animation anim : getAnimations()) {
            listModel.addElement(anim);
        }
        final JList<Animation> animationList = new JList<Animation>();
        animationList.setModel(listModel);
        final JScrollPane sp = new JScrollPane(animationList);
        sp.setPreferredSize(new Dimension(200, 300));

        final JButton browseBtn = new JButton("...");
        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a file.");

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final String path = fc.getSelectedFile().getAbsolutePath();
                    imageTf.setText(path);
                    imagePanel.setImage(path);
                    addButton.setEnabled(true);
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            PanelRenderer.this.texture = new Texture(path);
                        }
                    });
                    listModel.clear();
                    animations.clear();
                    imagePanel.getParent().revalidate();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewAnimationPopup(PanelRenderer.this, listModel, imageTf.getText()).
                        show(addButton, -1, addButton.getHeight());

            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Animation anim = animationList.getSelectedValue();
                removeAnimation(anim);
                listModel.removeElement(anim);
            }
        });

        animationList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    Animation anim = animationList.getSelectedValue();
                    removeButton.setEnabled(anim != null);
                }
            }
        });

        animationList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    Animation animation = animationList.getSelectedValue();
                    animation.showAnimationEditDialog();
                }
                super.mouseClicked(e);
            }
        });
        final JPanel addRemoveBtnPanel = new JPanel();
        addRemoveBtnPanel.add(addButton);
        addRemoveBtnPanel.add(removeButton);

        final JPanel animationPanel = new JPanel(new BorderLayout());
        animationPanel.add(addRemoveBtnPanel, BorderLayout.NORTH);
        animationPanel.add(sp);

        final JLabel imageLbl = new JLabel("Texture:");
        final JLabel nameLbl = new JLabel("Name:");
        final JTextField nameTf = new JTextField(20);
        nameTf.setText(name);
        nameTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = nameTf.getText().trim();
                if (!text.equals("")) {
                    name = text;
                    parent.getComponentList().updateUI();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = nameTf.getText().trim();
                if (!text.equals("")) {
                    name = text;
                    parent.getComponentList().updateUI();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        final JCheckBox enabledCb = new JCheckBox("Enabled");
        enabledCb.setSelected(enabled);
        enabledCb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                enabled = enabledCb.isSelected();
            }
        });
        JPanel leftPanel = new JPanel();
        GroupLayout layout = new GroupLayout(leftPanel);
        leftPanel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLbl).
                addComponent(imageLbl).addComponent(enabledCb));
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameTf).addComponent(imageTf));//.
        //addComponent(imagePanel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        //hGroup.addGroup(layout.createParallelGroup().addComponent(animationPanel));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLbl).
                addComponent(nameTf));//.addComponent(animationPanel));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(imageLbl).
                addComponent(imageTf).addComponent(browseBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb));
        //vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(imagePanel).
        //        addComponent(animationPanel));
        layout.setVerticalGroup(vGroup);
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        bottomPanel.add(animationPanel, BorderLayout.EAST);
        bottomPanel.add(imagePanel);

        final JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel);
        return mainPanel;
    }
    //endregion
}
