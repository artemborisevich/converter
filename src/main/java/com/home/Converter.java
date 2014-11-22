package com.home;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by User on 15.11.2014.
 */
public class Converter {

    public BufferedImage scaleImage(BufferedImage img, int width, int height) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        int startX = 0;
        int startY = 0;
        int widthNew = width;
        int heightNew = height;
        if (imgWidth * height < imgHeight * width) {
            widthNew = imgWidth * height / imgHeight;
            startX = width / 2 - widthNew / 2;
        } else {
            heightNew = imgHeight * width / imgWidth;
            startY = height / 2 - heightNew / 2;
        }
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            Composite comp = g.getComposite();
            g.setComposite(AlphaComposite.Clear);
            g.fillRect(0, 0, width, height);
            g.setComposite(comp);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(img, startX, startY, widthNew, heightNew, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }
}
