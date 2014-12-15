package com.home.service;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by User on 30.11.2014.
 */
public interface ImageService {

    void scaleImages(ThreadPoolExecutor threadPool, String urlFrom, String urlTo, Integer width);

    void scaleImage(ThreadPoolExecutor threadPool, File file, String urlTo, Integer width);

}
