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
                File brightenedFile = new File("bright_img.png");
                ImageIO.write(brightenedImage, "png", brightenedFile);
                System.out.println("Image brightened and saved as bright_img.png");

            } else {
                System.out.println("Failed to load the image.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while loading or saving an image: " + e.getMessage());
        }
    }
}