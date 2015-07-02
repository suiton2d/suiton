package com.suiton2d.editor.ui.theme;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * Created by bonazza on 4/26/2014.
 */
public class N2DDarkTheme extends DefaultMetalTheme {
    @Override
    public String getName() {
        return "N2D Dark";
    }

    @Override
    protected ColorUIResource getPrimary1() {
        return getSecondary1();
    }

    @Override
    protected ColorUIResource getPrimary2() {
        return getSecondary1();
    }

    @Override
    protected ColorUIResource getPrimary3() {
        return getPrimary2();
    }

    @Override
    protected ColorUIResource getSecondary1() {
        return new ColorUIResource(80, 80, 80);
    }

    @Override
    protected ColorUIResource getSecondary2() {
        return new ColorUIResource(40, 40, 40);
    }

    @Override
    protected ColorUIResource getSecondary3() {
        return new ColorUIResource(60, 63, 65);
    }

    @Override
    public ColorUIResource getControlShadow() {
        return getControlDarkShadow();
    }

    @Override
    public ColorUIResource getControlHighlight() {
        return getSecondary1();
    }

    @Override
    public ColorUIResource getPrimaryControlHighlight() {
        return getControlHighlight();
    }

    @Override
    public ColorUIResource getControl() {
        return getSecondary3();
    }

    @Override
    public ColorUIResource getPrimaryControl() {
        return getControl();
    }

    @Override
    public ColorUIResource getControlDarkShadow() {
        return getSecondary2();
    }

    @Override
    public ColorUIResource getPrimaryControlDarkShadow() {
        return getControlDarkShadow();
    }

    @Override
    public ColorUIResource getWindowBackground() {
        return new ColorUIResource(69, 73, 74);
    }

    @Override
    public ColorUIResource getBlack() {
        return new ColorUIResource(201, 201, 201);
    }

    @Override
    public ColorUIResource getMenuDisabledForeground() {
        return new ColorUIResource(153, 153, 153);
    }

    @Override
    public  ColorUIResource getTextHighlightColor() {
        return getSecondary1();
    }

    @Override
    public ColorUIResource getPrimaryControlInfo() {
        return getSecondary2();
    }

    @Override
    public ColorUIResource getMenuSelectedBackground() {
        return new ColorUIResource(75, 110, 175);
    }
}
