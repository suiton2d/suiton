package com.nebula2d.editor.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * Custom JPanel that displays an image
 */
public class ImagePanel extends JPanel {

    private ImageIcon icon;
    private JLabel emptyLbl;
    public ImagePanel() {
        emptyLbl = new JLabel("No image selected", SwingConstants.CENTER);
        setLayout(new BorderLayout());
        add(emptyLbl, BorderLayout.CENTER);
    }

    public void setImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Dimension newSize = calculateNewSize(img);
            Image scaledImage = resizeImage(img, newSize.width, newSize.height);

            icon = new ImageIcon(scaledImage);
            emptyLbl.setText("");
            emptyLbl.setIcon(icon);
        } catch (IOException e) {

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

        graphics.drawImage(og, 0, 0, w, h, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
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
