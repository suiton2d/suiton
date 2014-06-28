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

package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.assets.Sprite;
import com.nebula2d.editor.framework.components.KeyFrameAnimation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StillKeyFrameAnimationCanvas extends JPanel {

    private KeyFrameAnimation animation;
    private BufferedImage image;

    public StillKeyFrameAnimationCanvas(KeyFrameAnimation animation) {
        this.animation = animation;
        Sprite sprite = animation.getSprite();
        init();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(sprite.getBoundingWidth(), sprite.getBoundingHeight()));
    }

    public void init() {
        Sprite sprite = animation.getSprite();
        try {
            this.image = ImageIO.read(new File(sprite.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, null);
            //TODO: render lines appropriately
        }
    }
}
