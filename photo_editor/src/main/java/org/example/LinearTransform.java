package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LinearTransform {

    public static BufferedImage brighten(BufferedImage inputImg, double amount) {
        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImg.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (int) Math.min(255, ((rgb >> 16) & 0xFF) * amount);
                int green = (int) Math.min(255, ((rgb >> 8) & 0xFF) * amount);
                int blue = (int) Math.min(255, (rgb & 0xFF) * amount);

                int newColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
                outputImg.setRGB(x, y, newColor);
            }
        }
        return outputImg;
    }

    public BufferedImage darken(BufferedImage inputImg, double amount) {
        if (inputImg == null) {
            return null;
        }
        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImg.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (int) (((rgb >> 16) & 0xFF) * amount);
                int green = (int) (((rgb >> 8) & 0xFF) * amount);
                int blue = (int) ((rgb & 0xFF) * amount);

                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                outputImage.setRGB(x, y, newRgb);
            }
        }
        return outputImage;
    }
}