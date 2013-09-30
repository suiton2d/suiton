package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.components.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for manipulating GameObject components
 */
public class ComponentsDialog extends JDialog {

    private JPanel rightPanel;
    private GameObject gameObject;

    public ComponentsDialog(GameObject gameObject) {
        this.gameObject = gameObject;

        JPanel componentListPanel = forgeComponentsListPanel();
        rightPanel = forgeEmptyPanel();

        add(componentListPanel, BorderLayout.WEST);
        add(rightPanel);
        setSize(new Dimension(800, 600));
        setVisible(true);
    }

    private JPanel forgeEmptyPanel() {
        JPanel panel = new JPanel();
        JLabel emptyLbl = new JLabel("No component currently selected.");
        panel.add(emptyLbl);

        return panel;
    }

    private JPanel forgeComponentsListPanel() {
        final JList<Component> componentList = new JList<Component>();
        final DefaultListModel<Component> model = new DefaultListModel<Component>();
        componentList.setModel(model);


        populateComponentList(componentList);

        JScrollPane sp = new JScrollPane(componentList);
        sp.setPreferredSize(new Dimension(200, 400));

        final JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewComponentPopup(gameObject, componentList).show(addButton, -1, addButton.getHeight());
            }
        });
        final JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component selectedComponent = componentList.getSelectedValue();
                gameObject.removeComponent(selectedComponent);
                ((DefaultListModel<Component>) componentList.getModel()).removeElement(selectedComponent);
            }
        });
        removeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        componentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Component component = componentList.getSelectedValue();

                removeButton.setEnabled(component != null);

                if (component != null) {
                    //TODO: populate content.
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(sp);

        return mainPanel;
    }

    private void populateComponentList(JList<Component> listBox) {
        for (Component component : gameObject.getComponents()) {
            DefaultListModel<Component> model = (DefaultListModel<Component>) listBox.getModel();
            model.addElement(component);
        }
    }

    private JPanel forgeCommonTopPanel() {
        JLabel nameLbl = new JLabel("Name:");
        JTextField nameTf = new JTextField(20);

        JCheckBox enabledCb = new JCheckBox("Enabled");

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLbl).addComponent(enabledCb));
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameTf));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLbl).
                addComponent(nameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb));
        layout.setVerticalGroup(vGroup);

        return panel;
    }
}
