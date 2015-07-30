package com.suiton2d.editor.ui.theme;

import javax.swing.*;
import java.awt.*;

public class N2DListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (isSelected) {
            c.setBackground(new Color(13, 41, 62));
        }
        return c;
    }
}
