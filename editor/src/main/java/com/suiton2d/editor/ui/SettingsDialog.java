package com.suiton2d.editor.ui;

import com.suiton2d.editor.settings.ISettings;
import com.suiton2d.editor.settings.N2DSettings;
import com.suiton2d.editor.ui.controls.SuitonDialog;
import com.suiton2d.editor.ui.controls.SuitonList;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public class SettingsDialog extends SuitonDialog {

    private SuitonList<ISettings> settingsList;
    private JPanel mainPanel;
    private JPanel rightPanel;

    public SettingsDialog() {
        super("Nebula2D Settings", true);
        render();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setVisible(true);
    }

    private void render() {
        JScrollPane settingsPanel = createSettingsListPanel();
        mainPanel = new SuitonPanel(new BorderLayout());
        mainPanel.add(settingsPanel, BorderLayout.WEST);
        add(mainPanel);
        settingsList.setSelectedIndex(0);
    }

    private JScrollPane createSettingsListPanel() {
        settingsList = new SuitonList<>();
        final DefaultListModel<ISettings> listModel = new DefaultListModel<>();
        settingsList.setModel(listModel);
        Dimension windowSize = getPreferredSize();
        populateSettingsList();

        JScrollPane sp = new JScrollPane(settingsList);
        sp.setPreferredSize(new Dimension(windowSize.width / 4, windowSize.height));
        settingsList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;

            ISettings settings = settingsList.getSelectedValue();

            if (settings != null) {
                if (rightPanel != null)
                    mainPanel.remove(rightPanel);
                rightPanel = settings.createSettingsPanel(SettingsDialog.this);
                mainPanel.add(rightPanel);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });



        return sp;
    }

    private void populateSettingsList() {
        N2DSettings settings = MainFrame.getSettings();
        DefaultListModel<ISettings> model = (DefaultListModel<ISettings>)settingsList.getModel();
        model.addElement(settings.getGradleSettings());
    }
}
