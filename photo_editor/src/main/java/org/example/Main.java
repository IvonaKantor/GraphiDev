package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("image.png");
            BufferedImage image = ImageIO.read(inputFile);

            if (image == null) {
                System.out.println("No image found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while loading or saving an image: " + e.getMessage());
        }
    }
}