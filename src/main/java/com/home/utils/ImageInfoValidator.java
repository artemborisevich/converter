package com.home.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by User on 07.12.2014.
 */
public class ImageInfoValidator {

    private Logger log = LoggerFactory.getLogger(getClass());

    public boolean validateDir(String url) {
        File file = new File(url);

        if (!file.exists()){
            log.warn(url + " - does not exist");
            return false;
        }

        if (!file.isDirectory()) {
            log.warn(url + " - is not directory");
            return false;
        }
        return true;
    }

    public boolean validateWidth(String width){
        try {
            Integer.parseInt(width);
        } catch(NumberFormatException e) {
            log.warn(width + " - isn't integer");
            return false;
        }
        if (Integer.parseInt(width) <= 0) {
            log.warn(width + " <= 0");
            return false;
        }
        return true;
    }
}
