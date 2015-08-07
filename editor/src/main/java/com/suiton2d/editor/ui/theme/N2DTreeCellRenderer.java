package com.suiton2d.editor.ui.theme;

import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class N2DTreeCellRenderer extends DefaultTreeCellRenderer {

    public N2DTreeCellRenderer() {
        setBackgroundSelectionColor(new Color(13, 41, 62));
        setBackgroundNonSelectionColor(new Color(60, 63, 65));
    }
}
