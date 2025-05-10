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
            int value = (int)((double)hist[i] / max * height);
            g2d.drawLine(i + xOffset, height, i + xOffset, height - value);
        }
    }
}