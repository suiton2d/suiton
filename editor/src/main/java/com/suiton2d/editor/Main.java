package com.suiton2d.editor;

import com.suiton2d.editor.ui.MainFrame;
import com.suiton2d.editor.ui.theme.N2DDarkTheme;
import com.suiton2d.editor.util.PlatformUtil;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Main {

    public static void main(String[] args) {

        if (PlatformUtil.isMac()) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Nebula2D");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }


        try {
            MetalLookAndFeel.setCurrentTheme(new N2DDarkTheme());
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        if (PlatformUtil.isMac()) {
            new MainFrame();
        } else {
            SwingUtilities.invokeLater(MainFrame::new);
        }
    }
}
