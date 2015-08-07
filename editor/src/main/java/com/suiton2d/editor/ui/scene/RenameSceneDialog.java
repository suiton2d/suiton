package com.suiton2d.editor.ui.scene;

import com.suiton2d.editor.framework.Project;
import com.suiton2d.editor.ui.MainFrame;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneManager;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Dialog used for renaming the current scene.
 *
 * Created by bonazza on 7/24/14.
 */
public class RenameSceneDialog extends JDialog {

    private JTextField nameTf;

    public RenameSceneDialog() {
        setTitle("Rename Current Scene");
        setupContents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupContents() {
        nameTf = new JTextField(20);

        final Scene currentScene = SceneManager.getCurrentScene();
        final Color defaultFg = nameTf.getForeground();
        final JLabel nameLbl = new JLabel("Scene Name: ");
        final JButton okBtn = new JButton("Ok");
        final JButton cancelBtn = new JButton("Cancel");

        final JPanel namePanel = new JPanel();
        final JPanel btnPanel = new JPanel();

        nameTf.setText(currentScene.getName());

        namePanel.add(nameLbl);
        namePanel.add(nameTf);

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        nameTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validateText()) {
                    nameTf.setForeground(Color.RED);
                    okBtn.setEnabled(false);
                } else {
                    nameTf.setForeground(defaultFg);
                    okBtn.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!validateText()) {
                    nameTf.setForeground(Color.RED);
                    okBtn.setEnabled(false);
                } else {
                    nameTf.setForeground(defaultFg);
                    okBtn.setEnabled(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        okBtn.addActionListener(e -> {
            String newSceneName = nameTf.getText();
            currentScene.setName(newSceneName);
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        add(btnPanel, BorderLayout.SOUTH);
        add(namePanel, BorderLayout.CENTER);
        pack();
    }

    private boolean validateText() {
        String txt = nameTf.getText();
        Project project = MainFrame.getProject();
        return !txt.isEmpty() && !project.containsSceneWithName(txt);
    }
}
