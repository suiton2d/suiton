/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
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

import com.google.common.base.Function;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * Created by bonazza on 5/4/14.
 */
public class FloatValidatedDocumentListener implements DocumentListener {

    private JTextField tf;
    private Function<Float, Void> onSuccess;

    public FloatValidatedDocumentListener(JTextField tf, Function<Float, Void> onSuccess) {
        this.tf = tf;
        this.onSuccess = onSuccess;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (!tf.getText().isEmpty()) {
            Float f = StringUtil.toFloat(tf.getText());
            if (f != null)
                onSuccess.apply(f);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        if (!tf.getText().isEmpty()) {
            Float f = StringUtil.toFloat(tf.getText());
            if (f != null)
                onSuccess.apply(f);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
