package com.home;

import com.home.model.ImageInfo;
import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;
import com.home.utils.ImageInfoValidator;
import com.home.utils.ProgressChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Integer MAX_THREADS = Runtime.getRuntime().availableProcessors();

    private Logger log = LoggerFactory.getLogger(getClass());
    private String bufUrlFrom;
    private String bufUrlTo;
    private ImageService imageService = new ImageServiceImpl();
    private ImageInfo imageInfo = new ImageInfo();
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private ImageInfoValidator validator = new ImageInfoValidator();
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(MAX_THREADS, MAX_THREADS, 0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.enableHangupSupport();
        main.logic();
    }

    public void logic () {
            while (true) {
                try {
                    do {
                        System.out.println("Enter urlFrom: ");
                        imageInfo.setUrlFrom(in.readLine());
                    } while (!validator.validateDir(imageInfo.getUrlFrom()));
                    do {
                        System.out.println("Enter urlTo: ");
                        imageInfo.setUrlTo(in.readLine());
                    } while (!validator.validateDir(imageInfo.getUrlTo()));
                    do {
                        System.out.println("Enter width: ");
                        imageInfo.setWidth(in.readLine());
                    } while (!validator.validateWidth(imageInfo.getWidth()));
                } catch (IOException e) {
                    log.debug("input error", e);
                }
                ProgressChecker.check(threadPool, bufUrlFrom, bufUrlTo);

                bufUrlFrom = imageInfo.getUrlFrom();
                bufUrlTo = imageInfo.getUrlTo();

                imageService.scaleImagesInThread(threadPool, bufUrlFrom, bufUrlTo, Integer.valueOf(imageInfo.getWidth()));
            }
        }

    public void enableHangupSupport() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Inside Add Shutdown Hook");
                threadPool.shutdownNow();
            }
        });
        log.info("Shut Down Hook Attached.");
    }
}

//mvn exec:java -Dexec.mainClass="com.home.Main"