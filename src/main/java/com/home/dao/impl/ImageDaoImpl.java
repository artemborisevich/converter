package com.home.dao.impl;

import com.home.dao.ImageDao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.write;

/**
 * Created by User on 22.11.2014.
 */
public class ImageDaoImpl implements ImageDao {

    @Override
    public BufferedImage getImage(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void saveImage(BufferedImage img, String url) {
        try {
            write(img, "JPG", new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
