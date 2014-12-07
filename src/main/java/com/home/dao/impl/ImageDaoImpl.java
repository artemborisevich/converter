package com.home.dao.impl;

import com.home.dao.ImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by User on 22.11.2014.
 */
public class ImageDaoImpl implements ImageDao {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public File[] getFiles(String url) {
        return new File(url).listFiles();
    }
}
