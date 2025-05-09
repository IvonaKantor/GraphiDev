package org.example;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Arrays;

public class FilterOperations {

    public static BufferedImage applyMedianFilter(BufferedImage original) {
        BufferedImage result = ImageTransforms.copyImage(original);

        for (int y = 1; y < original.getHeight() - 1; y++) {
            for (int x = 1; x < original.getWidth() - 1; x++) {
                int[] reds = new int[9];
                int[] greens = new int[9];
                int[] blues = new int[9];
                int i = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color color = new Color(original.getRGB(x + kx, y + ky));
                        reds[i] = color.getRed();
                        greens[i] = color.getGreen();
                        blues[i] = color.getBlue();
                        i++;
                    }
                }

                Arrays.sort(reds);
                Arrays.sort(greens);
                Arrays.sort(blues);

                result.setRGB(x, y, new Color(reds[4], greens[4], blues[4]).getRGB());
            }
        }

        return result;
    }

    private static BufferedImage applyKernel(BufferedImage original, double[][] kernel) {
        BufferedImage result = ImageTransforms.copyImage(original);

        for (int y = 1; y < original.getHeight() - 1; y++) {
            for (int x = 1; x < original.getWidth() - 1; x++) {
                double sumRed = 0, sumGreen = 0, sumBlue = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color color = new Color(original.getRGB(x + kx, y + ky));
                        double weight = kernel[ky + 1][kx + 1];
                        sumRed += color.getRed() * weight;
                        sumGreen += color.getGreen() * weight;
                        sumBlue += color.getBlue() * weight;
                    }
                }

                int r = ImageTransforms.clamp((int) sumRed);
                int g = ImageTransforms.clamp((int) sumGreen);
                int b = ImageTransforms.clamp((int) sumBlue);

                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return result;
    }

    private static BufferedImage applyEdgeDetection(BufferedImage original,
                                                    double[][] kernelX, double[][] kernelY) {
        BufferedImage result = ImageTransforms.copyImage(original);

        for (int y = 1; y < original.getHeight() - 1; y++) {
            for (int x = 1; x < original.getWidth() - 1; x++) {
                double sumRedX = 0, sumGreenX = 0, sumBlueX = 0;
                double sumRedY = 0, sumGreenY = 0, sumBlueY = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color color = new Color(original.getRGB(x + kx, y + ky));
                        sumRedX += color.getRed() * kernelX[ky + 1][kx + 1];
                        sumGreenX += color.getGreen() * kernelX[ky + 1][kx + 1];
                        sumBlueX += color.getBlue() * kernelX[ky + 1][kx + 1];

                        sumRedY += color.getRed() * kernelY[ky + 1][kx + 1];
                        sumGreenY += color.getGreen() * kernelY[ky + 1][kx + 1];
                        sumBlueY += color.getBlue() * kernelY[ky + 1][kx + 1];
                    }
                }

                int r = ImageTransforms.clamp((int) Math.sqrt(sumRedX * sumRedX + sumRedY * sumRedY));
                int g = ImageTransforms.clamp((int) Math.sqrt(sumGreenX * sumGreenX + sumGreenY * sumGreenY));
                int b = ImageTransforms.clamp((int) Math.sqrt(sumBlueX * sumBlueX + sumBlueY * sumBlueY));

                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return result;
    }
}
