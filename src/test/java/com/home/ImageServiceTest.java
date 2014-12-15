package com.home;

import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by User on 30.11.2014.
 */
public class ImageServiceTest {

    private ImageService service = new ImageServiceImpl();
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File testImg;

    @Before
    public void createTestData() throws IOException {
        testImg = new File("src\\test\\resources\\test.jpg");
    }

    @Test
    public void testScaleImage() throws IOException {
        File src = folder.newFolder("src");
        service.scaleImage(threadPool, testImg, src.getPath(), 400);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BufferedImage image = ImageIO.read(new File(src.getPath() + "\\test.jpg"));
        assertNotNull(image);
        assertEquals(400, image.getWidth());
    }

    @Test
    public void testScaleNotImage() throws IOException {
        File src = folder.newFolder("src");
        File file = folder.newFile("test.txt");
        service.scaleImage(threadPool, file, src.getPath(), 400);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, src.listFiles().length);
    }
}