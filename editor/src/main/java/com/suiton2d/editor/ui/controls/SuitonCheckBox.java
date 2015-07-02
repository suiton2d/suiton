package com.suiton2d.editor.ui.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class SuitonCheckBox extends JCheckBox {

    public SuitonCheckBox() {
        setBackground(new Color(60, 63, 65));
    }

    public SuitonCheckBox(String text) {
        super(text);
        setBackground(new Color(60, 63, 65));
    }

    public SuitonCheckBox(String text, boolean selected) {
        super(text, selected);
        setBackground(new Color(60, 63, 65));
    }
}
