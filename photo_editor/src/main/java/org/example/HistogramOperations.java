package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class HistogramOperations {
    public static BufferedImage generateHistogram(BufferedImage original) {
        int[] redHist = new int[256];
        int[] greenHist = new int[256];
        int[] blueHist = new int[256];

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                Color color = new Color(original.getRGB(x, y));
                redHist[color.getRed()]++;
                greenHist[color.getGreen()]++;
                blueHist[color.getBlue()]++;
            }
        }

        int maxRed = Arrays.stream(redHist).max().getAsInt();
        int maxGreen = Arrays.stream(greenHist).max().getAsInt();
        int maxBlue = Arrays.stream(blueHist).max().getAsInt();

        int histWidth = 256;
        int histHeight = 100;
        BufferedImage histImage = new BufferedImage(
                histWidth * 3, histHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = histImage.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, histWidth * 3, histHeight);

        g2d.setColor(Color.RED);
        drawHistogram(g2d, redHist, maxRed, 0, histWidth, histHeight);

        g2d.setColor(Color.GREEN);
        drawHistogram(g2d, greenHist, maxGreen, histWidth, histWidth, histHeight);

        g2d.setColor(Color.BLUE);
        drawHistogram(g2d, blueHist, maxBlue, histWidth * 2, histWidth, histHeight);

        g2d.dispose();
        return histImage;
    }

    private static void drawHistogram(Graphics2D g2d, int[] hist, int max,
                                      int xOffset, int width, int height) {
        for (int i = 0; i < width; i++) {
            int value = (int) ((double) hist[i] / max * height);
            g2d.drawLine(i + xOffset, height, i + xOffset, height - value);
        }
    }

    private static int findHistogramMin(int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                return i;
            }
        }
        return 0;
    }

    private static int findHistogramMax(int[] histogram) {
        for (int i = histogram.length - 1; i >= 0; i--) {
            if (histogram[i] > 0) {
                return i;
            }
        }
        return 255;
    }

    private static int scaleValue(int value, int min, int max) {
        return (int) ((value - min) / (double) (max - min) * 255);
    }

    public static BufferedImage equalizeHistogram(BufferedImage original) {
        BufferedImage result = ImageTransforms.copyImage(original);

        int[] redHist = new int[256];
        int[] greenHist = new int[256];
        int[] blueHist = new int[256];

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                Color color = new Color(original.getRGB(x, y));
                redHist[color.getRed()]++;
                greenHist[color.getGreen()]++;
                blueHist[color.getBlue()]++;
            }
        }

        int[] redCDF = calculateCDF(redHist, original.getWidth() * original.getHeight());
        int[] greenCDF = calculateCDF(greenHist, original.getWidth() * original.getHeight());
        int[] blueCDF = calculateCDF(blueHist, original.getWidth() * original.getHeight());

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                Color color = new Color(result.getRGB(x, y));
                int r = redCDF[color.getRed()];
                int g = greenCDF[color.getGreen()];
                int b = blueCDF[color.getBlue()];
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return result;
    }

    private static int[] calculateCDF(int[] hist, int totalPixels) {
        int[] cdf = new int[256];
        cdf[0] = hist[0];

        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + hist[i];
        }

        int cdfMin = 0;
        for (int i = 0; i < 256; i++) {
            if (cdf[i] > 0) {
                cdfMin = cdf[i];
                break;
            }
        }

        for (int i = 0; i < 256; i++) {
            cdf[i] = (int) (((cdf[i] - cdfMin) / (double) (totalPixels - cdfMin)) * 255);
        }

        return cdf;
    }

    public static BufferedImage scaleHistogram(BufferedImage original) {
        int[] redHist = new int[256];
        int[] greenHist = new int[256];
        int[] blueHist = new int[256];

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                Color color = new Color(original.getRGB(x, y));
                redHist[color.getRed()]++;
                greenHist[color.getGreen()]++;
                blueHist[color.getBlue()]++;
            }
        }

        int redMin = findHistogramMin(redHist);
        int redMax = findHistogramMax(redHist);
        int greenMin = findHistogramMin(greenHist);
        int greenMax = findHistogramMax(greenHist);
        int blueMin = findHistogramMin(blueHist);
        int blueMax = findHistogramMax(blueHist);

        BufferedImage result = new BufferedImage(
                original.getWidth(), original.getHeight(), original.getType());

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                Color color = new Color(original.getRGB(x, y));

                int r = scaleValue(color.getRed(), redMin, redMax);
                int g = scaleValue(color.getGreen(), greenMin, greenMax);
                int b = scaleValue(color.getBlue(), blueMin, blueMax);

                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }
}