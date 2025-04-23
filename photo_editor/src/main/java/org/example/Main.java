package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("image.png");
            BufferedImage originalImage = ImageIO.read(inputFile);

            if (originalImage != null) {
                BufferedImage brightenedImage = LinearTransform.brighten(originalImage, 1.2);

            } else {
                System.out.println("Failed to load the image.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while loading or saving an image: " + e.getMessage());
        }
    }
}