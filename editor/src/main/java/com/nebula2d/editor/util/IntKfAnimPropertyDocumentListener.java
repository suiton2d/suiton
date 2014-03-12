/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.util;

import com.nebula2d.editor.framework.components.KeyFrameAnimation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IntKfAnimPropertyDocumentListener implements DocumentListener {

    public enum AnimationPropertyType {
        FRAME_WIDTH,
        FRAME_HEIGHT,
        START_FRAME,
        END_FRAME
    }

    private JTextField textField;
    private KeyFrameAnimation anim;
    private AnimationPropertyType propertyType;

    public IntKfAnimPropertyDocumentListener(JTextField textField, KeyFrameAnimation anim,
                                             AnimationPropertyType propertyType) {
        this.textField = textField;
        this.anim = anim;
        this.propertyType = propertyType;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        int value = validate();
        if (value == -1) {
            textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            return;
        }

        setValue(value);
        anim.init();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int value = validate();

        if (value >= 0) {
            setValue(value);
            anim.init();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) { }

    private int validate() {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void setValue(int value) {
        if (propertyType == AnimationPropertyType.FRAME_WIDTH) {
            anim.setFrameWidth(value);
        } else if (propertyType == AnimationPropertyType.FRAME_HEIGHT) {
            anim.setFrameHeight(value);
        } else if (propertyType == AnimationPropertyType.START_FRAME) {
            anim.setStartFrameIndex(value);
        } else {
            anim.setEndFrameIndex(value);
        }
    }
}
