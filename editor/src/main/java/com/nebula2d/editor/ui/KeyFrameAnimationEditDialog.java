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

import com.google.common.base.Function;
import com.nebula2d.editor.framework.components.KeyFrameAnimation;
import com.nebula2d.editor.ui.controls.N2DCheckBox;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FloatValidatedDocumentListener;
import com.nebula2d.editor.util.IntKfAnimPropertyDocumentListener;
import com.nebula2d.editor.util.StringUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class KeyFrameAnimationEditDialog extends JDialog {



    private KeyFrameAnimation animation;
    private AnimationRenderCanvas animatedCanvas;
    public KeyFrameAnimationEditDialog(KeyFrameAnimation animation) {
        this.animation = animation;
        create();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animatedCanvas.stop();
            }
        });
    }

    private void create() {
        N2DLabel frameWidthLbl = new N2DLabel("Frame Width:");
        N2DLabel frameHeightLbl = new N2DLabel("Frame height:");
        N2DLabel startFrameLbl = new N2DLabel("Start Frame:");
        N2DLabel endFrameLbl = new N2DLabel("End Frame:");
        N2DLabel speedLbl = new N2DLabel("Speed:");

        JTextField frameWidthTf = new JTextField(Integer.toString(animation.getFrameWidth()), 3);
        JTextField frameHeightTf = new JTextField(Integer.toString(animation.getFrameHeight()), 3);
        JTextField startFrameTf = new JTextField(Integer.toString(animation.getStartFrameIndex()), 3);
        JTextField endFrameTf = new JTextField(Integer.toString(animation.getEndFrameIndex()), 3);
        final JTextField speedTf = new JTextField(Float.toString(animation.getSpeed()), 3);

        frameWidthTf.getDocument().addDocumentListener(
                new IntKfAnimPropertyDocumentListener(frameWidthTf, animation,
                        IntKfAnimPropertyDocumentListener.AnimationPropertyType.FRAME_WIDTH)
        );
        frameHeightTf.getDocument().addDocumentListener(
                new IntKfAnimPropertyDocumentListener(frameHeightTf, animation,
                        IntKfAnimPropertyDocumentListener.AnimationPropertyType.FRAME_HEIGHT)
        );
        startFrameTf.getDocument().addDocumentListener(
                new IntKfAnimPropertyDocumentListener(startFrameTf, animation,
                        IntKfAnimPropertyDocumentListener.AnimationPropertyType.START_FRAME)
        );
        endFrameTf.getDocument().addDocumentListener(
                new IntKfAnimPropertyDocumentListener(endFrameTf, animation,
                        IntKfAnimPropertyDocumentListener.AnimationPropertyType.END_FRAME)
        );

        speedTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(speedTf, new Function<Float, Void>() {
            @Override
            public Void apply(Float input) {
                animation.setSpeed(input);
                animation.init();
                return null;
            }
        }));

        final N2DCheckBox wrapCb = new N2DCheckBox("Wrap", animation.wrap());
        wrapCb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                KeyFrameAnimationEditDialog.this.animation.setStateTime(0);
                KeyFrameAnimationEditDialog.this.animation.setWrap(wrapCb.isSelected());
            }
        });

        N2DPanel inputPanel = new N2DPanel();
        GroupLayout groupLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(frameWidthLbl).
                addComponent(frameHeightLbl).addComponent(startFrameLbl).addComponent(endFrameLbl).
                addComponent(speedLbl).addComponent(wrapCb));
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(frameWidthTf).
                addComponent(frameHeightTf).addComponent(startFrameTf).addComponent(endFrameTf).
                addComponent(speedTf));
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(frameWidthLbl).addComponent(frameWidthTf));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(frameHeightLbl).addComponent(frameHeightTf));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(startFrameLbl).addComponent(startFrameTf));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(endFrameLbl).addComponent(endFrameTf));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(speedLbl).addComponent(speedTf));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(wrapCb));
        groupLayout.setVerticalGroup(vGroup);

        N2DPanel rightPanel = new N2DPanel(new GridLayout(2, 1));
        StillKeyFrameAnimationCanvas stillCanvas = new StillKeyFrameAnimationCanvas(animation);
        animatedCanvas = new AnimationRenderCanvas(new AnimationRenderAdapter(animation));
        rightPanel.add(stillCanvas);
        rightPanel.add(animatedCanvas.getCanvas());
        setLayout(new BorderLayout());

        add(inputPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        setSize(new Dimension(800, 600));
        validate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        animatedCanvas.initCamera();
    }


}
