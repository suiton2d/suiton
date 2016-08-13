package com.suiton2d.editor.ui;

import com.suiton2d.editor.settings.ISettings;
import com.suiton2d.editor.settings.N2DSettings;
import com.suiton2d.editor.ui.controls.SuitonDialog;
import com.suiton2d.editor.ui.controls.SuitonList;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class SettingsDialog extends SuitonDialog {

    private SuitonList<ISettings> settingsList;
    private JPanel mainPanel;
    private JPanel rightPanel;

    public SettingsDialog(N2DSettings settings) {
        super("Nebula2D Settings", true);
        render(settings);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setVisible(true);
    }

    private void render(N2DSettings settings) {
        JScrollPane settingsPanel = createSettingsListPanel(settings);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(settingsPanel, BorderLayout.WEST);
        add(mainPanel);
        settingsList.setSelectedIndex(0);
    }

    private JScrollPane createSettingsListPanel(N2DSettings settings) {
        settingsList = new SuitonList<>();
        final DefaultListModel<ISettings> listModel = new DefaultListModel<>();
        settingsList.setModel(listModel);
        Dimension windowSize = getPreferredSize();
        populateSettingsList(settings);

        JScrollPane sp = new JScrollPane(settingsList);
        sp.setPreferredSize(new Dimension(windowSize.width / 4, windowSize.height));
        settingsList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;

            ISettings isettings = settingsList.getSelectedValue();

            if (isettings != null) {
                if (rightPanel != null)
                    mainPanel.remove(rightPanel);
                rightPanel = isettings.createSettingsPanel(SettingsDialog.this);
                mainPanel.add(rightPanel);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });



        return sp;
    }

    private void populateSettingsList(N2DSettings settings) {
        DefaultListModel<ISettings> model = (DefaultListModel<ISettings>)settingsList.getModel();
        model.addElement(settings.getGradleSettings());
    }
}
