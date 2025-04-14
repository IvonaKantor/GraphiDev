package org.example;

import java.awt.image.BufferedImage;

public class PowerTransform {
    public BufferedImage brighten(BufferedImage inputImg, double amount) {

        if (inputImg == null)
            return null;

        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImg.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int red = (int) Math.min(255, ((rgb >> 16) & 0xff) * amount);
                int green = (int) Math.min(255, ((rgb >> 8) & 0xff) * amount);
                int blue = (int) Math.min(255, (rgb & 0xff) * amount);

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
        BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int rgb = inputImg.getRGB(x, y);
            double red = Math.pow(((rgb >> 16) & 0xFF) / 255.0, amount) * 255;
            double green = Math.pow(((rgb >> 8) & 0xFF) / 255.0, amount) * 255;
            double blue = Math.pow((rgb & 0xFF) / 255.0, amount) * 255;
        }
    }
        return outputImg;
}
}

