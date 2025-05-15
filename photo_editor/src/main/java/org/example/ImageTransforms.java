package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTransforms {
    public static BufferedImage linearBrightness(BufferedImage original, double factor) {
        BufferedImage result = copyImage(original);

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                Color color = new Color(result.getRGB(x, y));
                int r = clamp((int) (color.getRed() * factor));
                int g = clamp((int) (color.getGreen() * factor));
                int b = clamp((int) (color.getBlue() * factor));
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }

    public static BufferedImage negative(BufferedImage original) {
        BufferedImage result = copyImage(original);

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                Color color = new Color(result.getRGB(x, y));
                result.setRGB(x, y, new Color(
                        255 - color.getRed(),
                        255 - color.getGreen(),
                        255 - color.getBlue()
                ).getRGB());
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

                int r = (int) (255 * Math.pow(normalizedR, gamma));
                int g = (int) (255 * Math.pow(normalizedG, gamma));
                int b = (int) (255 * Math.pow(normalizedB, gamma));

                result.setRGB(x, y, new Color(clamp(r), clamp(g), clamp(b)).getRGB());
            }
        }
        return result;
    }

    public static BufferedImage blendImages(BufferedImage img1, BufferedImage img2, int blendMode, double alpha) {
        Image tmp = img2.getScaledInstance(img1.getWidth(), img1.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage scaledImg2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);
        scaledImg2.getGraphics().drawImage(tmp, 0, 0, null);

        BufferedImage result = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                Color color1 = new Color(img1.getRGB(x, y));
                Color color2 = new Color(scaledImg2.getRGB(x, y));

                double r1 = color1.getRed() / 255.0;
                double g1 = color1.getGreen() / 255.0;
                double b1 = color1.getBlue() / 255.0;

                double r2 = color2.getRed() / 255.0;
                double g2 = color2.getGreen() / 255.0;
                double b2 = color2.getBlue() / 255.0;

                double r, g, b;

                switch (blendMode) {
                    case 1:
                        r = Math.min(1.0, r1 + r2);
                        g = Math.min(1.0, g1 + g2);
                        b = Math.min(1.0, b1 + b2);
                        break;

                    case 2:
                        r = Math.max(0.0, r1 + r2 - 1);
                        g = Math.max(0.0, g1 + g2 - 1);
                        b = Math.max(0.0, b1 + b2 - 1);
                        break;

                    case 3:
                        r = Math.abs(r1 - r2);
                        g = Math.abs(g1 - g2);
                        b = Math.abs(b1 - b2);
                        break;

                    case 4:
                        r = r1 * r2;
                        g = g1 * g2;
                        b = b1 * b2;
                        break;

                    case 5:
                        r = 1 - (1 - r1) * (1 - r2);
                        g = 1 - (1 - g1) * (1 - g2);
                        b = 1 - (1 - b1) * (1 - b2);
                        break;

                    case 6:
                        r = 1 - Math.abs(1 - r1 - r2);
                        g = 1 - Math.abs(1 - g1 - g2);
                        b = 1 - Math.abs(1 - b1 - b2);
                        break;

                    case 7:
                        r = Math.min(r1, r2);
                        g = Math.min(g1, g2);
                        b = Math.min(b1, b2);
                        break;

                    case 8:
                        r = Math.max(r1, r2);
                        g = Math.max(g1, g2);
                        b = Math.max(b1, b2);
                        break;

                    case 9:
                        r = r1 + r2 - 2 * r1 * r2;
                        g = g1 + g2 - 2 * g1 * g2;
                        b = b1 + b2 - 2 * b1 * b2;
                        break;

                    case 10:
                        r = (r1 < 0.5) ? 2 * r1 * r2 : 1 - 2 * (1 - r1) * (1 - r2);
                        g = (g1 < 0.5) ? 2 * g1 * g2 : 1 - 2 * (1 - g1) * (1 - g2);
                        b = (b1 < 0.5) ? 2 * b1 * b2 : 1 - 2 * (1 - b1) * (1 - b2);
                        break;

                    case 11:
                        r = (r2 < 0.5) ? 2 * r1 * r2 : 1 - 2 * (1 - r1) * (1 - r2);
                        g = (g2 < 0.5) ? 2 * g1 * g2 : 1 - 2 * (1 - g1) * (1 - g2);
                        b = (b2 < 0.5) ? 2 * b1 * b2 : 1 - 2 * (1 - b1) * (1 - b2);
                        break;

                    case 12:
                        r = (r2 < 0.5) ?
                                2 * r1 * r2 + r1 * r1 * (1 - 2 * r2) :
                                Math.sqrt(r1) * (2 * r2 - 1) + 2 * r1 * (1 - r2);
                        g = (g2 < 0.5) ?
                                2 * g1 * g2 + g1 * g1 * (1 - 2 * g2) :
                                Math.sqrt(g1) * (2 * g2 - 1) + 2 * g1 * (1 - g2);
                        b = (b2 < 0.5) ?
                                2 * b1 * b2 + b1 * b1 * (1 - 2 * b2) :
                                Math.sqrt(b1) * (2 * b2 - 1) + 2 * b1 * (1 - b2);
                        break;

                    case 13:
                        r = (r2 == 1) ? 1 : Math.min(1, r1 / (1 - r2));
                        g = (g2 == 1) ? 1 : Math.min(1, g1 / (1 - g2));
                        b = (b2 == 1) ? 1 : Math.min(1, b1 / (1 - b2));
                        break;

                    case 14:
                        r = (r2 == 0) ? 0 : Math.max(0, 1 - (1 - r1) / r2);
                        g = (g2 == 0) ? 0 : Math.max(0, 1 - (1 - g1) / g2);
                        b = (b2 == 0) ? 0 : Math.max(0, 1 - (1 - b1) / b2);
                        break;

                    case 15:
                        r = (r2 == 1) ? 1 : Math.min(1, (r1 * r1) / (1 - r2));
                        g = (g2 == 1) ? 1 : Math.min(1, (g1 * g1) / (1 - g2));
                        b = (b2 == 1) ? 1 : Math.min(1, (b1 * b1) / (1 - b2));
                        break;

                    case 16:
                    default:
                        r = r1 * alpha + r2 * (1 - alpha);
                        g = g1 * alpha + g2 * (1 - alpha);
                        b = b1 * alpha + b2 * (1 - alpha);
                        break;
                }

                int red = clamp((int) (r * 255));
                int green = clamp((int) (g * 255));
                int blue = clamp((int) (b * 255));

                result.setRGB(x, y, new Color(red, green, blue).getRGB());
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
                int r = clamp((int) (factor * (color.getRed() - 128) + 128));
                int g = clamp((int) (factor * (color.getGreen() - 128) + 128));
                int b = clamp((int) (factor * (color.getBlue() - 128) + 128));
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }

    public static BufferedImage copyImage(BufferedImage source) {
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