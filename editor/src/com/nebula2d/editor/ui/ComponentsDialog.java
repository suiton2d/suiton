package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.components.Component;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
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
    private JPanel mainPanel;
    private JList<Component> componentList;

    public ComponentsDialog(GameObject gameObject) {
        this.gameObject = gameObject;

        JPanel componentListPanel = forgeComponentsListPanel();
        rightPanel = forgeEmptyPanel();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(componentListPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel);
        add(mainPanel);
        setSize(new Dimension(800, 600));
        setResizable(false);
        setVisible(true);
    }

    public JList<Component> getComponentList() {
        return componentList;
    }

    private JPanel forgeEmptyPanel() {
        JPanel panel = new JPanel();
        JLabel emptyLbl = new JLabel("No component currently selected.");
        panel.add(emptyLbl);

        return panel;
    }

    private JPanel forgeComponentsListPanel() {
        componentList = new JList<Component>();
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
                mainPanel.remove(rightPanel);
                mainPanel.add(forgeEmptyPanel());
                revalidate();
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
                    System.out.println("component is not null!");
                    rightPanel = component.forgeComponentContentPanel(ComponentsDialog.this);
                    getLayout().removeLayoutComponent(rightPanel);
                    mainPanel.remove(rightPanel);
                    mainPanel.add(rightPanel);
                    revalidate();
                    //pack();
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(sp);

        return mainPanel;
    }

    private void populateComponentList(JList<Component> listBox) {
        DefaultListModel<Component> model = (DefaultListModel<Component>) listBox.getModel();
        for (Component component : gameObject.getComponents()) {
            model.addElement(component);
        }
    }

}
