package com.suiton2d.editor.ui.controls;

import com.suiton2d.editor.ui.theme.N2DListCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bonazza on 4/26/2014.
 */
public class SuitonList<T> extends JList<T> {

    public SuitonList() {
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DListCellRenderer());
    }

    public SuitonList(ListModel<T> dataModel) {
        super(dataModel);
        setBackground(new Color(60, 63, 65));
        setCellRenderer(new N2DListCellRenderer());
    }
}
