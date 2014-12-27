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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlWriter;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.AssetManager;
import com.nebula2d.editor.framework.assets.Sprite;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.ui.NewAnimationPopup;
import com.nebula2d.editor.ui.controls.ImagePanel;
import com.nebula2d.editor.ui.controls.N2DCheckBox;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DList;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FullBufferedReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SpriteRenderer extends AnimatedRenderer {

    public SpriteRenderer(String name) {
        super(name, RendererType.SPRITE_RENDERER);
        rendererType = RendererType.SPRITE_RENDERER;
    }

    @Override
    public Sprite getRenderable() {
        return (Sprite) renderable;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        Sprite sprite = (Sprite) renderable;
        if (sprite != null && sprite.isLoaded()) {

            if (currentAnim != -1) {
                getCurrentAnimation().renderStill(batcher, parent, cam);
                return;
            }

            sprite.render(parent, batcher, cam);
        }
    }

    @Override
    public N2DPanel forgeComponentContentPanel(final ComponentsDialog parent) {
        Sprite sprite = (Sprite) renderable;
        final ImagePanel imagePanel = new ImagePanel();
        final JButton addButton = new JButton("Add");
        addButton.setEnabled(false);
        final JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        final JTextField imageTf = new JTextField(20);

        if (sprite != null) {
            try {
                imagePanel.setImage(getRenderable().getPath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Failed to render image.");
            }
            imageTf.setText(sprite.getPath());
            addButton.setEnabled(true);
        }

        final DefaultListModel<Animation> listModel = new DefaultListModel<>();
        getAnimations().forEach(listModel::addElement);
        final N2DList<Animation> animationList = new N2DList<>();
        animationList.setModel(listModel);
        animationList.addListSelectionListener(e -> {
            Animation animation = animationList.getSelectedValue();
            if (animation != null && animation.isRenderable())
                setCurrentAnimation(animation);
        });
        if (currentAnim > -1)
            animationList.setSelectedValue(getCurrentAnimation(), true);
        final JScrollPane sp = new JScrollPane(animationList);
        sp.setPreferredSize(new Dimension(200, 300));

        final JButton browseBtn = new JButton("...");
        browseBtn.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Select a file.");

            if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                final String path = fc.getSelectedFile().getAbsolutePath();
                imageTf.setText(path);
                try {
                    imagePanel.setImage(path);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(parent, "Failed to render image.");
                }
                addButton.setEnabled(true);
                int currScene = MainFrame.getProject().getCurrentSceneIdx();
                SpriteRenderer.this.renderable = AssetManager.getInstance().getOrCreateSprite(currScene, path);

                listModel.clear();
                animations.clear();
                imagePanel.getParent().revalidate();
            }
        });

        addButton.addActionListener(e -> new NewAnimationPopup(SpriteRenderer.this, listModel, imageTf.getText()).
                show(addButton, -1, addButton.getHeight()));

        removeButton.addActionListener(e -> {
            Animation anim = animationList.getSelectedValue();
            removeAnimation(anim);
            listModel.removeElement(anim);
        });

        animationList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                Animation anim = animationList.getSelectedValue();

                if (anim != null && anim.isRenderable())
                    setCurrentAnimation(anim);

                removeButton.setEnabled(anim != null);
            }
        });

        animationList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    Animation animation = animationList.getSelectedValue();
                    animation.showAnimationEditDialog(SpriteRenderer.this);
                }
                super.mouseClicked(e);
            }
        });

        final N2DPanel addRemoveBtnPanel = new N2DPanel();
        addRemoveBtnPanel.add(addButton);
        addRemoveBtnPanel.add(removeButton);

        final N2DPanel animationPanel = new N2DPanel(new BorderLayout());
        animationPanel.add(addRemoveBtnPanel, BorderLayout.NORTH);
        animationPanel.add(sp);

        final N2DLabel imageLbl = new N2DLabel("Texture:");
        final N2DLabel nameLbl = new N2DLabel("Name:");
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
            }
        });

        final N2DCheckBox enabledCb = new N2DCheckBox("Enabled");
        enabledCb.setSelected(enabled);
        enabledCb.addChangeListener(e -> enabled = enabledCb.isSelected());
        N2DPanel leftPanel = new N2DPanel();
        GroupLayout layout = new GroupLayout(leftPanel);
        leftPanel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLbl).
                addComponent(imageLbl).addComponent(enabledCb));
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameTf).addComponent(imageTf));
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLbl).
                addComponent(nameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(imageLbl).
                addComponent(imageTf).addComponent(browseBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb));
        layout.setVerticalGroup(vGroup);
        final N2DPanel bottomPanel = new N2DPanel(new BorderLayout());
        bottomPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        bottomPanel.add(animationPanel, BorderLayout.EAST);
        bottomPanel.add(imagePanel);

        final N2DPanel mainPanel = new N2DPanel(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel);
        return mainPanel;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        int tmp = fr.readIntLine();

        if (tmp == 1) {
            int currScene = MainFrame.getProject().getCurrentSceneIdx();
            String path = fr.readLine();
            renderable = AssetManager.getInstance().getOrCreateSprite(currScene, path);
            renderable.load(fr);
        }

        int size = fr.readIntLine();
        for (int i = 0; i < size; ++i) {
            String animName = fr.readLine();
            Animation.AnimationType animType = Animation.AnimationType.valueOf(fr.readLine());
            Animation animation = null;
            if (animType == Animation.AnimationType.KEY_FRAME)
                animation = new KeyFrameAnimation(animName, (Sprite) renderable);

            if (animation == null) {
                throw new IOException("Failed to load project.");
            }

            animation.load(fr);
            animations.add(animation);
        }
        currentAnim = fr.readIntLine();
    }

    @Override
    public void build(XmlWriter sceneXml, XmlWriter assetsXml, String sceneName) throws  IOException {
        super.build(sceneXml, assetsXml, sceneName);
        Sprite sprite = (Sprite) renderable;
        sceneXml.attribute("sprite", sprite.getBuildPath());
        assetsXml.element("asset").attribute("path", sprite.getBuildPath()).
                attribute("assetType", "SPRITE").attribute("sceneName", sceneName);
        assetsXml.pop();
    }

    @Override
    public boolean isMoveable() {
        return true;
    }
}
