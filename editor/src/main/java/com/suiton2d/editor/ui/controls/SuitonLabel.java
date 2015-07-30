package com.suiton2d.editor.ui.controls;

import javax.swing.*;
import java.awt.*;

public class SuitonLabel extends JLabel {

    public SuitonLabel(String text) {
        super(text);
        setBackground(new Color(60, 63, 65));
    }

    public SuitonLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setBackground(new Color(60, 63, 65));
    }
}
