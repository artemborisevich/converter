package com.home;

import com.home.dao.ImageDao;
import com.home.service.ImageService;
import com.home.service.impl.ImageServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 30.11.2014.
 */

public class ImageServiceTest {

    @InjectMocks
    private ImageService service = new ImageServiceImpl();

    @Mock
    private ImageDao dao;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File testImg;

    @Before
    public void createTestData() throws IOException {
        testImg = new File("src\\test\\resources\\test.jpg");
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testScaleImage() throws IOException {
        File dir = folder.newFolder("dir");
        service.scaleImage(testImg, dir.getPath(), 400);
        BufferedImage image = ImageIO.read(new File(dir.getPath() + "/" + testImg.getName()));
        assertEquals(400, image.getWidth());
    }

    @Test
    public void testScaleNotImage() throws IOException {
        File dir = folder.newFolder("dir");
        File file = folder.newFile("test.txt");
        service.scaleImage(file, dir.getPath(), 400);
        assertEquals(0, dir.listFiles().length);
    }

    @Test
    public void testScaleImagesInThread() throws IOException {
        File[] files = {testImg};

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 4, 0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
        Mockito.when(dao.getFiles("dir")).thenReturn(files);
        service.scaleImagesInThread(threadPool, "dir", "dir", 400);
        verify(dao).getFiles("dir");
    }
}