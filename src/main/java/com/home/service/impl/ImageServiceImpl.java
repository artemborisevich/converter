package com.home.service.impl;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;
import com.home.service.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
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

    public void scaleImage(ThreadPoolExecutor threadPool, final File file, final String urlTo, final Integer width) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thumbnails.of(file)
                            .width(width)
                            .outputFormat("JPEG")
                            .outputQuality(0.99)
                            .toFiles(new File(urlTo), Rename.NO_CHANGE);
                } catch (UnsupportedFormatException uf) {
                    log.debug("unsupported format", uf);
                } catch (IOException e) {
                    log.debug("input error", e);
                }
            }
        });
    }

    @Override
    public void scaleImages(ThreadPoolExecutor threadPool, String urlFrom, final String urlTo, final Integer width) {
        File[] files = dao.getFiles(urlFrom);
        for (final File file : files) {
            scaleImage(threadPool, file, urlTo, width);
        }
    }
}