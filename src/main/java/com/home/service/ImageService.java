package com.home.service;

import java.awt.image.BufferedImage;

/**
 * Created by User on 30.11.2014.
 */
public interface ImageService {

    BufferedImage getImage(String url);

    void saveImage(BufferedImage img, String url);

    public BufferedImage scaleImage(BufferedImage img, Integer width, Integer height);
}
