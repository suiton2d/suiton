package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.framework.components.KeyFrameAnimation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by bonazza on 1/11/14.
 */
public class StillKeyFrameAnimationCanvas extends JPanel {

    private KeyFrameAnimation animation;
    private BufferedImage image;

    public StillKeyFrameAnimationCanvas(KeyFrameAnimation animation) {
        this.animation = animation;
        Texture tex = animation.getTexture();
        init();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(tex.getWidth(), tex.getHeight()));
    }

    public void init() {
        Texture texture = animation.getTexture();
        try {
            this.image = ImageIO.read(new File(texture.getPath()));
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
