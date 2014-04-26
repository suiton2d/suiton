package com.nebula2d.editor.ui.controls;

import com.nebula2d.editor.ui.theme.N2DListCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class N2DList<T> extends JList<T> {

    public N2DList() {
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DListCellRenderer());
    }

    public N2DList(ListModel<T> dataModel) {
        super(dataModel);
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DListCellRenderer());
    }
}
