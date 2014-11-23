package com.home;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;
import com.home.utils.impl.ImageUtilsImpl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by User on 15.11.2014.
 */
public class Logic {

    ImageDao imageDao = new ImageDaoImpl();

    public void start() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        while (true) {
            try {
                System.out.println("Enter urlFrom: ");
                String urlFrom = in.readLine();
                System.out.println("Enter urlTo: ");
                String urlTo = in.readLine();
                System.out.println("Enter width: ");
                Integer width = Integer.valueOf(in.readLine());
                System.out.println("Enter height: ");
                Integer height = Integer.valueOf(in.readLine());
                BufferedImage img = imageDao.getImage(urlFrom);
                threadPool.submit(new ImageUtilsImpl(img, width, height, urlTo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}