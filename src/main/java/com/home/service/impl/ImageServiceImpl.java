package com.home.service.impl;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;
import com.home.model.ImageInfo;
import com.home.service.ImageService;
import com.home.util.Validator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @Override
    public ImageInfo getImageInfo() {
        ImageInfo imageInfo = new ImageInfo();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Validator validator = new Validator();
        try {
            do {
                System.out.println("Enter urlFrom: ");
                imageInfo.setUrlFrom(in.readLine());
            } while (!validator.dirValidator(imageInfo.getUrlFrom()));
            do {
                System.out.println("Enter urlTo: ");
                imageInfo.setUrlTo(in.readLine());
            } while (!validator.dirValidator(imageInfo.getUrlTo()));
            do {
                System.out.println("Enter width: ");
                imageInfo.setWidth(in.readLine());
            } while (!validator.widthValidator(imageInfo.getWidth()));
        } catch (IOException e) {
            log.debug(null, e);
        }
        return imageInfo;
    }
}