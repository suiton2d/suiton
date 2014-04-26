package com.nebula2d.editor.ui.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class N2DCheckBox extends JCheckBox {

    public N2DCheckBox() {
        setBackground(new Color(60, 63, 65));
    }

    public N2DCheckBox(String text) {
        super(text);
        setBackground(new Color(60, 63, 65));
    }

    public N2DCheckBox(String text, boolean selected) {
        super(text, selected);
        setBackground(new Color(60, 63, 65));
    }
}
