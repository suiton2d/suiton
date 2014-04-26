package com.nebula2d.editor.ui.controls;

import com.nebula2d.editor.ui.theme.N2DTreeCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class N2DTree extends JTree {

    public N2DTree() {
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DTreeCellRenderer());
    }
}
