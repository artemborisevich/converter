package com.home;

import com.home.utils.ImageInfoValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 15.12.2014.
 */
public class ImageInfoValidatorTest {

    private ImageInfoValidator validator = new ImageInfoValidator();

    @Before
    public void createTestData() throws IOException {
        File file = new File("test.txt");
        file.createNewFile();
        File dir = new File("testFolder");
        dir.mkdir();
    }

    @After
    public void cleanUp() {
        File file = new File("test.txt");
        file.delete();
        File dir = new File("dir");
        dir.delete();
    }

    @Test
    public void testValidateDirNotExist() {
        assertFalse(validator.validateDir("test"));
    }

    @Test
    public void testValidateDirIsNotDir() {
        assertFalse(validator.validateDir("test.txt"));
    }

    @Test
    public void testValidateDir() {
        assertTrue(validator.validateDir("testFolder"));
    }

    @Test
    public void testValidateWidthIsString () {
        assertFalse(validator.validateWidth("test"));
    }

    @Test
    public void testValidateWidthIsDecimal () {
        assertFalse(validator.validateWidth("10.3"));
    }

    @Test
    public void testValidateWidthIsNegative () {
        assertFalse(validator.validateWidth("-3"));
    }

    @Test
    public void testValidateWidth () {
        assertTrue(validator.validateWidth("10"));
    }
}
