package com.home;

import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;
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
    private ImageService imageService = new ImageServiceImpl();
    private String urlFrom;
    private String urlTo;
    private String width;
    private String bufUrlFrom;
    private String bufUrlTo;
    private Integer bufWidth;
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(MAX_THREADS, MAX_THREADS, 0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
    private Validator validator = new Validator();

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.enableHangupSupport();
        main.logic();
    }

    public void logic () {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    do {
                        System.out.println("Enter urlFrom: ");
                        urlFrom = in.readLine();
                    } while (!validator.dirValidator(urlFrom));
                    do {
                        System.out.println("Enter urlTo: ");
                        urlTo = in.readLine();
                    } while (!validator.dirValidator(urlFrom));
                    do {
                        System.out.println("Enter width: ");
                        width = in.readLine();
                    } while (!validator.widthValidator(width));
                } catch (IOException e) {
                    log.debug(null, e);
                }

                imageService.waiter(threadPool, bufUrlFrom, bufUrlTo);

                bufUrlFrom = urlFrom;
                bufUrlTo = urlTo;
                bufWidth = Integer.valueOf(width);

                imageService.scaleImages(threadPool, bufUrlFrom, bufUrlTo, bufWidth);
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