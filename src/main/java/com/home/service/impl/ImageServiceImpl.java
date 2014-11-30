package com.home.service.impl;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;
import com.home.service.ImageService;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * Created by User on 30.11.2014.
 */
public class ImageServiceImpl implements ImageService {

    private ImageDao dao = new ImageDaoImpl();


    @Override
    public BufferedImage getImage(String url) {
        return dao.getImage(url);
    }

    @Override
    public void saveImage(BufferedImage img, String url) {
        dao.saveImage(img, url);
    }

    @Override
    public BufferedImage scaleImage(BufferedImage img, Integer width, Integer height) {
        return  Scalr.resize(img, width, height);
    }
}
