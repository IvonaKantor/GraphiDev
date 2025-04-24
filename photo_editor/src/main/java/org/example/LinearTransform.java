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
        return null;
    }

    public BufferedImage negative(BufferedImage inputImg) {
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
                int red = 255 - ((rgb >> 16) & 0xFF);
                int green = 255 - ((rgb >> 8) & 0xFF);
                int blue = 255 - (rgb & 0xFF);

                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                outputImage.setRGB(x, y, newRgb);
            }
        }
        return outputImage;
    }
}