package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTransforms {
    public static BufferedImage linearBrightness(BufferedImage original, double factor) {
        BufferedImage result = copyImage(original);

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                Color color = new Color(result.getRGB(x, y));
                int r = clamp((int)(color.getRed() * factor));
                int g = clamp((int)(color.getGreen() * factor));
                int b = clamp((int)(color.getBlue() * factor));
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }


    public static BufferedImage powerTransform(BufferedImage original, double gamma) {
        BufferedImage result = copyImage(original);

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                Color color = new Color(result.getRGB(x, y));
                double normalizedR = color.getRed() / 255.0;
                double normalizedG = color.getGreen() / 255.0;
                double normalizedB = color.getBlue() / 255.0;

                int r = (int)(255 * Math.pow(normalizedR, gamma));
                int g = (int)(255 * Math.pow(normalizedG, gamma));
                int b = (int)(255 * Math.pow(normalizedB, gamma));

                result.setRGB(x, y, new Color(clamp(r), clamp(g), clamp(b)).getRGB());
            }
        }
        return result;
    }

    public static BufferedImage adjustContrast(BufferedImage original, double contrast) {
        double factor = (259 * (contrast + 255)) / (255 * (259 - contrast));
        BufferedImage result = copyImage(original);

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                Color color = new Color(result.getRGB(x, y));
                int r = clamp((int)(factor * (color.getRed() - 128) + 128));
                int g = clamp((int)(factor * (color.getGreen() - 128) + 128));
                int b = clamp((int)(factor * (color.getBlue() - 128) + 128));
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }

    static BufferedImage copyImage(BufferedImage source) {
        BufferedImage image = new BufferedImage(
                source.getWidth(), source.getHeight(), source.getType());
        Graphics g = image.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return image;
    }

    static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}