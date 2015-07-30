package com.suiton2d.editor.ui.controls;

import com.suiton2d.editor.ui.theme.N2DTreeCellRenderer;

import javax.swing.*;
import java.awt.*;

public class SuitonTree extends JTree {

    public SuitonTree() {
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DTreeCellRenderer());
        setModel(null);
    }
}
