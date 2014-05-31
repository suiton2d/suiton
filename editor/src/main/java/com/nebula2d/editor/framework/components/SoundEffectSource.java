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
import com.badlogic.gdx.audio.Music;
import com.nebula2d.editor.framework.assets.AssetManager;
import com.nebula2d.editor.framework.assets.SoundEffect;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.ui.controls.N2DCheckBox;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SoundEffectSource extends Component {

    private SoundEffect soundEffect;

    public SoundEffectSource(String name) {
        super(name);
        componentType = ComponentType.SFX;
    }

    @Override
    public N2DPanel forgeComponentContentPanel(final ComponentsDialog parent) {
        final N2DLabel nameLbl = new N2DLabel("Name:");
        final N2DLabel fileLbl = new N2DLabel("File:");
        final N2DLabel filePathLbl = new N2DLabel(soundEffect != null ? soundEffect.getPath() : "");
        final JTextField nameTf = new JTextField(name, 20);
        final N2DCheckBox enabledCb = new N2DCheckBox("Enabled", enabled);
        final N2DCheckBox loopCb = new N2DCheckBox("Loop", soundEffect != null && soundEffect.isLooping());
        final JButton browseBtn = new JButton("...");
        final JButton mediaBtn = new JButton("Play");
        mediaBtn.setEnabled(soundEffect != null);

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a file.");

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final String path = fc.getSelectedFile().getAbsolutePath();
                    filePathLbl.setText(path);
                    int currScene = MainFrame.getProject().getCurrentSceneIdx();
                    SoundEffectSource.this.soundEffect = AssetManager.getInstance().
                            getOrCreateSoundEffect(currScene, path);
                    mediaBtn.setEnabled(true);
                }
            }
        });

        enabledCb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                enabled = enabledCb.isSelected();
            }
        });

        loopCb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                soundEffect.setLoop(loopCb.isSelected());
            }
        });
        final Music.OnCompletionListener onSfxCompleteListener =
                new com.badlogic.gdx.audio.Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(com.badlogic.gdx.audio.Music music) {
                        mediaBtn.setEnabled(true);
                    }
                };
        mediaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaBtn.setEnabled(false);
                playSoundEffect(onSfxCompleteListener);
            }
        });

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

        N2DPanel panel = new N2DPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLbl).
                addComponent(fileLbl).addComponent(enabledCb).addComponent(loopCb));
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameTf).addComponent(filePathLbl));//.
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        hGroup.addGroup(layout.createParallelGroup().addComponent(mediaBtn));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLbl).
                addComponent(nameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(fileLbl).
                addComponent(filePathLbl).addComponent(browseBtn).addComponent(mediaBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb).
                addComponent(loopCb));
        layout.setVerticalGroup(vGroup);

        return panel;
    }

    private void playSoundEffect(final Music.OnCompletionListener listener) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
            soundEffect.play();
            soundEffect.setOnCompleteListener(listener);
            }
        });
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {

        int tmp = fr.readIntLine();
        if (tmp != 0) {
            String path = fr.readLine();
            int currScene = MainFrame.getProject().getCurrentSceneIdx();
            soundEffect = AssetManager.getInstance().getOrCreateSoundEffect(currScene, path);
            soundEffect.load(fr);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        if (soundEffect == null) {
            fw.writeIntLine(0);
        } else {
            fw.writeIntLine(1);
            soundEffect.save(fw);
        }
    }
}
