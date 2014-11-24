package com.nebula2d.editor.ui.controls;

import com.nebula2d.editor.util.PlatformUtil;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Created by bonazza on 9/19/14.
 */
public class N2DDialog extends JDialog {

    public N2DDialog(String title) {
        this(title, true);
    }

    public N2DDialog(String title, boolean autoSize) {
        setTitle(title);
        if (autoSize)
            initSize();
    }

    private void initSize() {
        Dimension screenSize = PlatformUtil.getScreenSize();
        setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
    }
}
