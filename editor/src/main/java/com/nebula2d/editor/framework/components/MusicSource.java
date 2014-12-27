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
import com.badlogic.gdx.utils.XmlWriter;
import com.nebula2d.editor.framework.assets.AssetManager;
import com.nebula2d.editor.framework.assets.MusicTrack;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.ui.controls.N2DCheckBox;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.IOException;

public class MusicSource extends Component {

    private MusicTrack musicTrack;

    public MusicSource(String name) {
        super(name);
        componentType = ComponentType.MUSIC;
    }

    @Override
    public N2DPanel forgeComponentContentPanel(final ComponentsDialog parent) {

        final N2DLabel nameLbl = new N2DLabel("Name:");
        final N2DLabel fileLbl = new N2DLabel("File:");
        final N2DLabel filePathLbl = new N2DLabel("");
        final JTextField nameTf = new JTextField(name, 20);
        final N2DCheckBox enabledCb = new N2DCheckBox("Enabled", enabled);
        final N2DCheckBox loopCb = new N2DCheckBox("Loop", musicTrack != null && musicTrack.isLooping());
        final JButton browseBtn = new JButton("...");
        final JButton mediaBtn = new JButton("Play");
        mediaBtn.setEnabled(musicTrack != null);
        if (musicTrack != null) {
            filePathLbl.setText(musicTrack.getPath());
        }
        browseBtn.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Select a file.");

            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                final String path = fc.getSelectedFile().getAbsolutePath();
                filePathLbl.setText(path);
                int currScene = MainFrame.getProject().getCurrentSceneIdx();
                MusicSource.this.musicTrack = AssetManager.getInstance().
                        getOrCreateMusicTrack(currScene, path);
                mediaBtn.setEnabled(true);
            }
        });
        final com.badlogic.gdx.audio.Music.OnCompletionListener onMusicCompleteListener =
                music -> mediaBtn.setText("Play");

        enabledCb.addChangeListener(e -> enabled = enabledCb.isSelected());
        loopCb.addChangeListener(e -> musicTrack.setLoop(loopCb.isSelected()));
        mediaBtn.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            if (btn.getText().equals("Play")) {
                btn.setText("Stop");
                Gdx.app.postRunnable(() -> {
                    musicTrack.play();
                    musicTrack.setOnCompleteListener(onMusicCompleteListener);
                });
            } else {
                btn.setText("Play");
                Gdx.app.postRunnable(musicTrack::stop);
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
                addComponent(nameTf));//.addComponent(animationPanel));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(fileLbl).
                addComponent(filePathLbl).addComponent(browseBtn).addComponent(mediaBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb).
                addComponent(loopCb));
        layout.setVerticalGroup(vGroup);

        return panel;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {

        int tmp = fr.readIntLine();
        if (tmp != 0) {
            String path = fr.readLine();
            int currScene = MainFrame.getProject().getCurrentSceneIdx();
            musicTrack = AssetManager.getInstance().getOrCreateMusicTrack(currScene, path);
            musicTrack.load(fr);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        if (musicTrack == null) {
            fw.writeIntLine(0);
        } else {
            fw.writeIntLine(1);
            musicTrack.save(fw);
        }
    }

    @Override
    public void build(XmlWriter sceneXml, XmlWriter assetsXml, String sceneName) throws  IOException {
        super.build(sceneXml, assetsXml, sceneName);
        sceneXml.attribute("track", musicTrack.getBuildPath());
        assetsXml.element("asset").attribute("path", musicTrack.getBuildPath()).attribute("assetType", "MUSIC").
                attribute("sceneName", sceneName);
        assetsXml.pop();
    }
}
