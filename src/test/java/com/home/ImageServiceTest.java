package com.home;

import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by User on 30.11.2014.
 */
public class ImageServiceTest {

    private ImageService service = new ImageServiceImpl();

    @Test
    public void getImage(){
        assertNotNull(service.getImage("d:\\help.jpg"));
    }
}
