package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.Scene;
import com.nebula2d.editor.ui.controls.N2DDialog;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;

/**
 *
 * Created by bonazza on 11/19/14.
 */
public class BuildProgressDialog extends N2DDialog implements BuildProgressUpdateListener {

    private JProgressBar progressBar;
    private JLabel nowLoadingLbl;

    public BuildProgressDialog(String projectName) {
        super(String.format("Building %s...", projectName));
        init();
    }

    private void init() {
        final JPanel mainPanel = new JPanel(new BorderLayout());

        nowLoadingLbl = new JLabel();
        progressBar = new JProgressBar();

        mainPanel.add(nowLoadingLbl, BorderLayout.SOUTH);
        mainPanel.add(progressBar, BorderLayout.CENTER);

        add(mainPanel);
    }

    @Override
    public void onBuildProgressUpdate(Scene scene, int idx, int size) {
        nowLoadingLbl.setText(String.format("Building scene %s... (%d/%d", scene.getName(), idx, size));
        progressBar.setValue(idx);
    }
}
