package com.home;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;

public class Main {

    ImageDao imageDao = new ImageDaoImpl();

    public static void main(String[] args) {
        Logic l = new Logic();
        l.start();
    }
}