package com.nebula2d.editor.ui;


import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.components.Component;
import com.nebula2d.editor.framework.components.PanelRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewComponentPopup extends JPopupMenu {

    private GameObject gameObject;
    private DefaultListModel<Component> listModel;
    private JList<Component> list;

    public NewComponentPopup(GameObject gameObject, JList<Component> list) {
        this.list = list;
        this.gameObject = gameObject;
        this.listModel = (DefaultListModel<Component>) list.getModel();

        JMenu rendererMenu = new JMenu("Renderer");
        JMenuItem panelRendererMenuItem = rendererMenu.add("PanelRenderer");
        panelRendererMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (NewComponentPopup.this.gameObject.getRenderer() != null) {
                    JOptionPane.showMessageDialog(NewComponentPopup.this, "This GameObject already has a renderer attached.");
                    return;
                }

                PanelRenderer panelRenderer = new PanelRenderer("");
                new NewComponentDialog(panelRenderer);
            }
        });

        add(rendererMenu);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });
    }

    private class NewComponentDialog extends JDialog {
        private Component component;

        public NewComponentDialog(Component component) {
            this.component = component;
            final JLabel errorMessage = new JLabel("You must enter a valid name for the component.");
            errorMessage.setForeground(Color.red);
            errorMessage.setVisible(false);

            final JLabel nameLbl = new JLabel("Name:");
            final JTextField nameTf = new JTextField(20);
            JPanel namePanel = new JPanel();
            namePanel.add(nameLbl);
            namePanel.add(nameTf);

            JButton okBtn = new JButton("Ok");
            okBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameTf.getText();
                    if (!validateText(name)) {
                        errorMessage.setVisible(true);
                        return;
                    }

                    errorMessage.setVisible(false);
                    NewComponentDialog.this.component.setName(name);
                    gameObject.addComponent(NewComponentDialog.this.component);

                    //There is an NPE here that is seemingly harmless. Just ignore it for now.
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listModel.addElement(NewComponentDialog.this.component);
                            list.setSelectedValue(NewComponentDialog.this.component, true);
                        }
                    });

                    dispose();
                }
            });
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(okBtn);
            buttonPanel.add(cancelBtn);

            add(errorMessage, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);
            add(namePanel);
            pack();
            setVisible(true);
        }

        private boolean validateText(String text) {
            return !text.trim().equals("");
        }
    }
}
