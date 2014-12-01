package com.home;

import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Integer MAX_THREADS = Runtime.getRuntime().availableProcessors();

    private Logger log = LoggerFactory.getLogger(getClass());
    private ImageService imageService = new ImageServiceImpl();
    private String urlFrom;
    private String urlTo;
    private Integer width;
    private Integer height;

    public static void main(String[] args) {
        Main main = new Main();
        main.enableHangupSupport();
        main.logic();
    }

    public void logic (){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        try {
            while (true) {
                System.out.println("Enter urlFrom: ");
                urlFrom = in.readLine();
                System.out.println("Enter urlTo: ");
                urlTo = in.readLine();
                System.out.println("Enter width: ");
                width = Integer.valueOf(in.readLine());
                System.out.println("Enter height: ");
                height = Integer.valueOf(in.readLine());

            if (Thread.activeCount() == MAX_THREADS + 1) {
                System.out.println("wait");
                while (Thread.activeCount() == MAX_THREADS + 1){}
                System.out.println("go");
            }
                threadPool.submit(new Thread() {
                    @Override
                    public void run() {
                        BufferedImage img = imageService.getImage(urlFrom);
                        BufferedImage newImg = imageService.scaleImage(img, width, height);
                        imageService.saveImage(newImg, urlTo);
                    }
                });
        }
        } catch (IOException e) {
            log.debug(null, e);
            threadPool.shutdown();
        }
    }

    public void enableHangupSupport() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Inside Add Shutdown Hook");
                System.exit(0);
            }
        });
        log.info("Shut Down Hook Attached.");
    }
}