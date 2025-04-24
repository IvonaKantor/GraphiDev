package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LinearTransform {

    public static BufferedImage brighten(BufferedImage inputImg, double amount) {

        if (inputImg == null) {
            return null;
        }

        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(inputImg.getRGB(x, y));
                int red = (int) Math.min(255, color.getRed() + amount);
                int green = (int) Math.min(255, color.getGreen() + amount);
                int blue = (int) Math.min(255, color.getBlue() + amount);
                outputImg.setRGB(x, y, new Color(red, green, blue).getRGB());
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
        BufferedImage outputImage = new BufferedImage(width, height, inputImg.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(inputImg.getRGB(x, y));
                int red = (int) Math.min(255, color.getRed() - amount);
                int green = (int) Math.min(255, color.getGreen() - amount);
                int blue = (int) Math.min(255, color.getBlue() - amount);
                outputImage.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
        return outputImage;
    }

    public BufferedImage negative(BufferedImage inputImg) {
        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, inputImg.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(inputImg.getRGB(x, y));
                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();
                outputImage.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
        return outputImage;
    }
}