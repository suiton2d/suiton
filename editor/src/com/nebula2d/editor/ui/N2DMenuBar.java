package com.nebula2d.editor.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 9/6/13
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class N2DMenuBar extends JMenuBar {

    private MainFrame parent;

    private JMenu fileMenu;

    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem exitMenuItem;

    public N2DMenuBar(MainFrame parent) {
        this.parent = parent;

        fileMenu = new JMenu("File");

        newMenuItem = fileMenu.add("New Project");
        openMenuItem = fileMenu.add("Open Project");
        exitMenuItem = fileMenu.add("Exit Nebula2D");

        add(fileMenu);
        bindMenuItems();
    }

    private void bindMenuItems() {
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewProjectDialog(parent);
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
    }

    //region accessors
    public JMenuItem getNewMenuItem() {
        return newMenuItem;
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    //endregion
}
