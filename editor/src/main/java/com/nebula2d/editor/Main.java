package com.nebula2d.editor;

import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.util.PlatformUtil;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        if (PlatformUtil.isMac()) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Nebula2D");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
