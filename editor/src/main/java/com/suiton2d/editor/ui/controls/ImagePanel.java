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

package com.suiton2d.editor.ui.controls;

import com.badlogic.gdx.files.FileHandle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Custom JPanel that displays an image
 */
public class ImagePanel extends JPanel {

    private JLabel emptyLbl;

    public ImagePanel() {
        emptyLbl = new JLabel("No image selected", SwingConstants.CENTER);
        add(emptyLbl);
    }

    public void setImage(FileHandle fileHandle) throws IOException {
        if (fileHandle != null) {
            BufferedImage img = ImageIO.read(fileHandle.read());
            Dimension newSize = calculateNewSize(img);
            Image scaledImage = resizeImage(img, newSize.width, newSize.height);

            ImageIcon icon = new ImageIcon(scaledImage);
            emptyLbl.setText("");
            emptyLbl.setIcon(icon);
        }
    }

    private Image resizeImage(Image og, int w, int h) {
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage newImage = new BufferedImage(w, h, type);
        Graphics2D graphics = newImage.createGraphics();

        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.drawImage(og, 0, 0, w, h, (img, infoflags, x, y, width, height) -> false);
        graphics.dispose();

        return newImage;
    }

    private Dimension calculateNewSize(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        while (w > 400 || h > 400) {
            w /= 2;
            h /= 2;
        }

        return new Dimension(w, h);
    }
}
