package com.home;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;

public class Main {

    ImageDao imageDao = new ImageDaoImpl();

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutDownInterceptor());
        new ShutDownInterceptor().start();
    }

    private static final class ShutDownInterceptor extends Thread {
        @Override
        public void run() {
            Logic l = new Logic();
            l.start();
        }
    }
}