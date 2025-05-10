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
    }
}