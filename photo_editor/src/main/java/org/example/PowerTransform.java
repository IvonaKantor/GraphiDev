package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class PowerTransform {
    public static BufferedImage powerTransform(BufferedImage inputImage, double gamma) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        double c = 255.0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(inputImage.getRGB(x, y));
                int red = (int) Math.min(255, c * Math.pow(color.getRed() / 255.0, gamma));
                int green = (int) Math.min(255, c * Math.pow(color.getGreen() / 255.0, gamma));
                int blue = (int) Math.min(255, c * Math.pow(color.getBlue() / 255.0, gamma));
                outputImage.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
        return outputImage;
    }

    public static BufferedImage brightenPower(BufferedImage inputImage, double gamma) {
        return powerTransform(inputImage, gamma);
    }
}