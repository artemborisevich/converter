package com.home.utils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by User on 07.12.2014.
 */
public class ProgressChecker {

    public static void check(ThreadPoolExecutor threadPool, String urlFrom, String urlTo) {
        if (threadPool.getTaskCount() != threadPool.getCompletedTaskCount() && threadPool.getTaskCount() != 0) {
            System.out.println("Previous conversion from " + urlFrom + " to " + urlTo + " is in progress, waiting");
            while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
                Thread.yield();
            }
            System.out.println("Starting to process entered data!");
        }
    }
}