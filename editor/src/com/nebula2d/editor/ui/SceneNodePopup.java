package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.BaseSceneNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SceneNodePopup extends JPopupMenu {


    public SceneNodePopup(final BaseSceneNode node) {

        JMenuItem deleteMenuItem = add("Delete");

        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                node.remove();
            }
        });
    }


}
