package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.AbstractSound;
import com.nebula2d.editor.framework.assets.Music;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.ui.ComponentsDialog;

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.bean.playerbean.MediaPlayer;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MusicSource extends Component {

    private Music music;
    private MediaPlayer mp;
    public MusicSource(String name) {
        super(name);
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
    }

    @Override
    public JPanel forgeComponentContentPanel(ComponentsDialog parent) {
        mp = new MediaPlayer();
        final JLabel nameLbl = new JLabel("Name:");
        final JLabel fileLbl = new JLabel("File:");
        final JLabel filePathLbl = new JLabel("");
        final JTextField nameTf = new JTextField(name, 20);
        final JCheckBox enabledCb = new JCheckBox("Enabled", enabled);
        final JCheckBox loopCb = new JCheckBox("Loop", music != null && music.isLooping());
        final JButton browseBtn = new JButton("...");
        final JButton mediaBtn = new JButton("Play");
        mediaBtn.setEnabled(music != null);
        if (music != null) {
            filePathLbl.setText(music.getPath());
            setMedia(music.getPath());
        }
        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a file.");

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final String path = fc.getSelectedFile().getAbsolutePath();
                    filePathLbl.setText(path);
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            setMedia(path);
                            MusicSource.this.music = new Music(path);
                        }
                    });
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
                music.setLoop(loopCb.isSelected());
            }
        });
        mediaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if (btn.getText().equals("Play")) {
                    btn.setText("Stop");
                    mp.start();
                } else {
                    mp.stop();
                }
            }
        });

        JPanel panel = new JPanel();
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

    private void setMedia(String path) {
        File mediaFile = new File(path);
        URL mediaUrl;
        try {
            mediaUrl = mediaFile.toURI().toURL();
        } catch (MalformedURLException e1) {
            return;
        }

        MediaLocator mediaLocator = new MediaLocator(mediaUrl);
        mp.setMediaLocator(mediaLocator);
    }
}
