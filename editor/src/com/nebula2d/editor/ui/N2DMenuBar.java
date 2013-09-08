package com.nebula2d.editor.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class N2DMenuBar extends JMenuBar {

    //region members
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem exitMenuItem;

    private JMenuItem componentsMenuItem;
    //endregion

    //region constructors
    public N2DMenuBar() {

        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        newMenuItem = fileMenu.add("New Project");
        openMenuItem = fileMenu.add("Open Project");
        exitMenuItem = fileMenu.add("Exit Nebula2D");

        componentsMenuItem = toolsMenu.add("Components");
        componentsMenuItem.setEnabled(false);

        add(fileMenu);
        add(toolsMenu);
        bindMenuItems();
    }

    //endregion

    //region internal methods
    private void bindMenuItems() {
        //region file menu bindings
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewProjectDialog();
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: implement
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Prompt on exit
                System.exit(0);
            }
        });
        //endregion

        //region tool menu bindings
        componentsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: implement
            }
        });
        //endregion
    }
    //endregion

    //region accessors
    public JMenuItem getComponentsMenuItem() {
        return componentsMenuItem;
    }
    //endregion
}
