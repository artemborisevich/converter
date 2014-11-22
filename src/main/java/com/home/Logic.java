package com.home;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static javax.imageio.ImageIO.write;

/**
 * Created by User on 15.11.2014.
 */
public class Logic {
    String src;
    String dir;
    Integer x;
    Integer y;
    Converter converter = new Converter();

    public void start () {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter scr: ");
            src = in.readLine();
            System.out.println("Enter dir: ");
            dir = in.readLine();
            System.out.println("Enter x: ");
            x = Integer.valueOf(in.readLine());
            System.out.println("Enter y: ");
            y = Integer.valueOf(in.readLine());
            BufferedImage img = ImageIO.read(new File(src));
            img = converter.scaleImage(img, x, y);
            write(img, "JPG", new File(dir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
