package com.nebula2d.editor.ui.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class N2DLabel extends JLabel {

    public N2DLabel(String text) {
        super(text);
        setBackground(new Color(60, 63, 65));
    }

    public N2DLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setBackground(new Color(60, 63, 65));
    }
}
