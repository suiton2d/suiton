package com.suiton2d.editor.ui.controls;

import com.suiton2d.editor.util.PlatformUtil;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Created by bonazza on 9/19/14.
 */
public class SuitonDialog extends JDialog {

    public SuitonDialog(String title) {
        this(title, false);
    }

    public SuitonDialog(String title, boolean autoSize) {
        setTitle(title);
        if (autoSize)
            initSize();
    }

    private void initSize() {
        Dimension screenSize = PlatformUtil.getScreenSize();
        setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
    }
}
