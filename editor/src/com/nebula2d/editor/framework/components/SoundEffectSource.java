package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Music;
import com.nebula2d.editor.framework.assets.SoundEffect;
import com.nebula2d.editor.ui.ComponentsDialog;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.MediaLocator;
import javax.media.StopEvent;
import javax.media.bean.playerbean.MediaPlayer;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundEffectSource extends Component {

    private SoundEffect soundEffect;

    public SoundEffectSource(String name) {
        super(name);
    }

    public SoundEffect getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(SoundEffect soundEffect) {
        this.soundEffect = soundEffect;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
    }

    @Override
    public JPanel forgeComponentContentPanel(ComponentsDialog parent) {
        final JLabel nameLbl = new JLabel("Name:");
        final JLabel fileLbl = new JLabel("File:");
        final JLabel filePathLbl = new JLabel(soundEffect != null ? soundEffect.getPath() : "");
        final JTextField nameTf = new JTextField(name, 20);
        final JCheckBox enabledCb = new JCheckBox("Enabled", enabled);
        final JCheckBox loopCb = new JCheckBox("Loop", soundEffect.isLooping());
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
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            SoundEffectSource.this.soundEffect = new SoundEffect(path);
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
                soundEffect.setLoop(loopCb.isSelected());
            }
        });

        mediaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaBtn.setEnabled(false);
                playSoundEffect(filePathLbl.getText(), mediaBtn);
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
                addComponent(nameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(fileLbl).
                addComponent(filePathLbl).addComponent(browseBtn).addComponent(mediaBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb).
                addComponent(loopCb));
        layout.setVerticalGroup(vGroup);

        return panel;
    }

    private void playSoundEffect(String path, final JButton mediaBtn) {
        File mediaFile = new File(path);
        URL mediaUrl;
        try {
            mediaUrl = mediaFile.toURI().toURL();
        } catch (MalformedURLException e1) {
            return;
        }

        MediaLocator mediaLocator = new MediaLocator(mediaUrl);
        final MediaPlayer mp = new MediaPlayer();
        mp.setMediaLocator(mediaLocator);
        mp.addControllerListener(new ControllerListener() {
            @Override
            public void controllerUpdate(ControllerEvent controllerEvent) {
                if (controllerEvent instanceof StopEvent) {
                    mediaBtn.setEnabled(true);
                }
            }
        });
        mp.start();
    }
}
