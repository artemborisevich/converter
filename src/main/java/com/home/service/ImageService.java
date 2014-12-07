package com.home.service;

import com.home.model.ImageInfo;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by User on 30.11.2014.
 */
public interface ImageService {

    void scaleImages(ThreadPoolExecutor threadPool, String urlFrom, String urlTo, Integer width);

    void waiter(ThreadPoolExecutor threadPool, String urlFrom, String urlTo);

    ImageInfo getImageInfo();
}
