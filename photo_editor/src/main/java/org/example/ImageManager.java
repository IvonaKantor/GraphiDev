package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageManager {

    public BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    public void saveImage(BufferedImage image, String filename) {
        try{
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Saved image to " + filename);
        }catch(IOException e){
            System.out.println("Error saving image" + e.getMessage());
        }
    }
}