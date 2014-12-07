package com.home.service.impl;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;
import com.home.service.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by User on 30.11.2014.
 */
public class ImageServiceImpl implements ImageService {

    private Logger log = LoggerFactory.getLogger(getClass());
    private ImageDao dao = new ImageDaoImpl();

    @Override
    public void scaleImages(ThreadPoolExecutor threadPool, String urlFrom, final String urlTo, final Integer width) {
        File[] files = dao.getFiles(urlFrom);
        for (final File file : files) {
            threadPool.submit(new Thread() {
                @Override
                public void run() {
                    try {
                        Thumbnails.of(file)
                                .width(width)
                                .outputFormat("JPEG")
                                .outputQuality(0.99)
                                .toFiles(new File(urlTo), Rename.NO_CHANGE);
                    } catch (IOException e) {
                        log.debug(null, e);
                    } catch (IllegalArgumentException el) {
                        log.debug(null, el);
                    }
                }
            });
        }
    }

    @Override
    public void waiter(ThreadPoolExecutor threadPool, String urlFrom, String urlTo) {
        if (threadPool.getTaskCount() != threadPool.getCompletedTaskCount() && threadPool.getTaskCount() != 0) {
                    System.out.println("Previous conversion from " + urlFrom + " to " + urlTo + " is in progress, waiting");
                    while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
                        Thread.yield();
                    }
                    System.out.println("Starting to process entered data!");
                }
    }
}