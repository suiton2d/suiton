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
        setTitle(title);
        Dimension screenSize = PlatformUtil.getScreenSize();
        setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
    }
}
