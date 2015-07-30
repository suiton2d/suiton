package com.suiton2d.editor.ui.build;

import com.suiton2d.editor.ui.controls.SuitonDialog;

/**
 *
 * Created by bonazza on 10/11/14.
 */
public class BuildDialog extends SuitonDialog {

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
