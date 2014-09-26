package com.nebula2d.editor.settings;

import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Properties;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public class GradleSettings implements ISettings {

    private String gradleHome = "";
    private Properties properties;

    public GradleSettings(Properties properties) {
        this.properties = properties;
    }

    public void loadFromProperties() {
        gradleHome = properties.getProperty("gradle.home", "");
    }

    public String getGradleHome() {
        return gradleHome;
    }

    @Override
    public JPanel createSettingsPanel(JDialog parent) {
        JLabel gradleHomeLbl = new N2DLabel("Gradle Home: ");
        JTextField gradleHomeTf = new JTextField(gradleHome, 20);
        JButton applyBtn = new JButton("Apply");
        applyBtn.setEnabled(false);
        JButton cancelBtn = new JButton("Cancel");

        gradleHomeTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyBtn.setEnabled(!gradleHomeTf.getText().isEmpty());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyBtn.setEnabled(!gradleHomeTf.getText().isEmpty());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        applyBtn.addActionListener(e -> {
            gradleHome = gradleHomeTf.getText().trim();
            properties.setProperty("gradle.home", gradleHome);
            applyBtn.setEnabled(false);
        });

        cancelBtn.addActionListener(e -> parent.dispose());

        final JPanel buttonPanel = new N2DPanel();
        buttonPanel.add(applyBtn);
        buttonPanel.add(cancelBtn);

        JPanel topPanel = new N2DPanel();
        GroupLayout layout = new GroupLayout(topPanel);
        topPanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(gradleHomeLbl));
        hGroup.addGroup(layout.createParallelGroup().addComponent(gradleHomeTf));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(gradleHomeLbl).addComponent(gradleHomeTf));
        layout.setVerticalGroup(vGroup);

        JPanel mainPanel = new N2DPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    @Override
    public String toString() {
        return "gradle";
    }
}
