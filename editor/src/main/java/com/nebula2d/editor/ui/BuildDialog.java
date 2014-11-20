package com.nebula2d.editor.ui;

import com.nebula2d.editor.ui.controls.N2DDialog;

/**
 *
 * Created by bonazza on 10/11/14.
 */
public class BuildDialog extends N2DDialog {

    public BuildDialog() {
        super("Project Build");
        setup();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setVisible(true);
    }

    public void setup() {

    }
}
