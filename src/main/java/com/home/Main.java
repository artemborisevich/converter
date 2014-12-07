package com.home;

import com.home.model.ImageInfo;
import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;
import com.home.utils.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Integer MAX_THREADS = Runtime.getRuntime().availableProcessors();

    private Logger log = LoggerFactory.getLogger(getClass());
    private ImageService imageService = new ImageServiceImpl();
    private String bufUrlFrom;
    private String bufUrlTo;
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(MAX_THREADS, MAX_THREADS, 0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.enableHangupSupport();
        main.logic();
    }

    public void logic () {
            while (true) {
                ImageInfo imageInfo = imageService.getImageInfo();
                Waiter.wait(threadPool, bufUrlFrom, bufUrlTo);

                bufUrlFrom = imageInfo.getUrlFrom();
                bufUrlTo = imageInfo.getUrlTo();

                imageService.scaleImages(threadPool, bufUrlFrom, bufUrlTo, Integer.valueOf(imageInfo.getWidth()));
            }
        }

    public void enableHangupSupport() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Inside Add Shutdown Hook");
                threadPool.shutdown();
            }
        });
        log.info("Shut Down Hook Attached.");
    }
}