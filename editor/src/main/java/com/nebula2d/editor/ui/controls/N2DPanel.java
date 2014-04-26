package com.nebula2d.editor.ui.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class N2DPanel extends JPanel {

    public N2DPanel(LayoutManager layout) {
        super(layout);
        setBackground(new Color(60, 63, 65));
    }

    public N2DPanel() {
        setBackground(new Color(60, 63, 65));
    }
}
