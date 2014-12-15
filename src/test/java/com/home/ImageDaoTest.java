package com.home;

import com.home.dao.ImageDao;
import com.home.dao.impl.ImageDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 15.12.2014.
 */
public class ImageDaoTest {

    private ImageDao dao = new ImageDaoImpl();

    @Before
    public void createTestData() throws IOException {
        File dir = new File("testFolder");
        dir.mkdir();
        File file1 = new File("testFolder/file1.txt");
        file1.createNewFile();
        File file2 = new File("testFolder/file2.jpg");
        file2.createNewFile();
        File file3 = new File("testFolder/file3.gif");
        file3.createNewFile();
    }

    @After
    public void cleanUp() {
        File dir = new File("testFolder");
        for (File file : dir.listFiles()) {
            file.delete();
        }
        dir.delete();
    }

    @Test
    public void testGetFiles() {
        File[] files = dao.getFiles("testFolder");
        assertEquals(3, files.length);
        for (File file: files) {
            assertTrue(file.isFile());
        }
    }
}
