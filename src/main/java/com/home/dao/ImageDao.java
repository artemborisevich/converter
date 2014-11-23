package com.home.dao;

import java.awt.image.BufferedImage;

/**
 * Created by User on 22.11.2014.
 */
public interface ImageDao {

    BufferedImage getImage(String url);

    void saveImage(BufferedImage img, String url);
}
