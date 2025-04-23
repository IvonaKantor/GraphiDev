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
                File brightenedFile = new File("brightened_image.png");
                ImageIO.write(brightenedImage, "png", brightenedFile);
                System.out.println("Image brightened and saved as brightened_image.png");

                LinearTransform linearTransform = new LinearTransform();
                BufferedImage darkenedImage = linearTransform.darken(originalImage, 0.8);
                File darkenedFile = new File("darkened_image.png");
                ImageIO.write(darkenedImage, "png", darkenedFile);
                System.out.println("Image darkened and saved as darkened_image.png");

            } else {
                System.out.println("Failed to load the image.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while loading or saving an image: " + e.getMessage());
        }
    }
}