package org.example;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageOperations {
    public void openFirstImage(ImagePanel panel) {
        openImage(panel, true);
    }

    public void openSecondImage(ImagePanel panel) {
        openImage(panel, false);
    }

    private void openImage(ImagePanel panel, boolean isFirst) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage image = ImageIO.read(fileChooser.getSelectedFile());
                if (isFirst) {
                    panel.setFirstImage(image);
                } else {
                    panel.setSecondImage(image);
                }
            } catch (IOException e) {
                showError("Error loading image");
            }
        }
    }

    public void saveFirstImage(BufferedImage image) {
        saveImage(image, "Save image 1");
    }

    public void saveSecondImage(BufferedImage image) {
        saveImage(image, "Save image 2");
    }

    private void saveImage(BufferedImage image, String title) {
        if (image == null) {
            showError("No image to save");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                String format = path.toLowerCase().endsWith(".png") ? "png" : "jpg";
                ImageIO.write(image, format, new File(path));
            } catch (IOException e) {
                showError("Error saving image");
            }
        }
    }

    public void blendImages(ImagePanel panel, int blendMode, double alpha) {
        BufferedImage img1 = panel.getFirstImage();
        BufferedImage img2 = panel.getSecondImage();

        if (img1 == null || img2 == null) {
            showError("Both images must be loaded");
            return;
        }

        BufferedImage blended = ImageTransforms.blendImages(img1, img2, blendMode, alpha);
        panel.setFirstImage(blended);
    }

    public void applyOperation(String operation, ImagePanel panel, JSlider slider) {
        BufferedImage image = panel.getSelectedImage();
        if (image == null) {
            showError("Select Image Before Applying the Action");
            return;
        }

        BufferedImage result;
        double param = slider.getValue() / 100.0;

        switch (operation) {
            case "Brightening":
                result = adjustBrightness(image, 1.0 + param);
                break;
            case "Darkening":
                result = adjustBrightness(image, 1.0 - param);
                break;
            case "Power Transform (Brightening)":
                result = ImageTransforms.powerTransform(image, 1.0 - param);
                break;
            case "Power Transform (Darkening)":
                result = ImageTransforms.powerTransform(image, 1.0 + param);
                break;
            case "Negative":
                result = negative(image);
                break;
            case "Contrast":
                result = adjustContrast(image, param * 3);
                break;
            case "Gauss filter":
                result = Gauss(image);
                break;
            case "Edge detection":
                result = calculateEdges(image);
                break;
            case "Thresholding":
                result = threshold(image, (int) (param * 255));
                break;
            case "Sepia":
                result = Sepia(image);
                break;
            case "Blur":
                result = blur(image);
                break;
            case "Generate RGB Histogram":
                result = HistogramOperations.generateHistogram(image);
                break;
            case "Equalize Histogram":
                result = HistogramOperations.equalizeHistogram(image);
                break;
            case "Scale Histogram":
                result = HistogramOperations.scaleHistogram(image);
                break;
            case "Low-pass Filter":
                result = FilterOperations.applyLowPassFilter(image);
                break;
            case "Roberts Filter":
                result = FilterOperations.applyRobertsFilter(image);
                break;
            case "Prewitt Filter":
                result = FilterOperations.applyPrewittFilter(image);
                break;
            case "Sobel Filter":
                result = FilterOperations.applySobelFilter(image);
                break;
            case "Laplace Filter":
                result = FilterOperations.applyLaplaceFilter(image);
                break;
            case "Min Filter":
                result = FilterOperations.applyMinFilter(image);
                break;
            case "Max Filter":
                result = FilterOperations.applyMaxFilter(image);
                break;
            case "Median Filter":
                result = FilterOperations.applyMedianFilter(image);
                break;
            default:
                return;
        }

        panel.setSelectedImage(result);
    }

    private BufferedImage adjustBrightness(BufferedImage original, double factor) {
        return ImageTransforms.linearBrightness(original, factor);
    }

    private BufferedImage negative(BufferedImage original) {
        return ImageTransforms.negative(original);
    }

    private BufferedImage adjustContrast(BufferedImage original, double contrast) {
        return ImageTransforms.adjustContrast(original, contrast);
    }

    private BufferedImage Gauss(BufferedImage original) {
        float[] matrix = {
                1 / 16f, 2 / 16f, 1 / 16f,
                2 / 16f, 4 / 16f, 2 / 16f,
                1 / 16f, 2 / 16f, 1 / 16f
        };
        return applyConvolutionFilter(original, matrix);
    }

    private BufferedImage calculateEdges(BufferedImage original) {
        float[] matrix = {
                -1, -1, -1,
                -1, 8, -1,
                -1, -1, -1
        };
        return applyConvolutionFilter(original, matrix);
    }

    private BufferedImage applyConvolutionFilter(BufferedImage original, float[] matrix) {
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        Kernel kernel = new Kernel(3, 3, matrix);
        ConvolveOp op = new ConvolveOp(kernel);
        op.filter(original, result);
        return result;
    }

    private BufferedImage threshold(BufferedImage original, int threshold) {
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                Color color = new Color(original.getRGB(x, y));
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                result.setRGB(x, y, gray > threshold ? Color.WHITE.getRGB() : Color.BLACK.getRGB());
            }
        }
        return result;
    }

    private BufferedImage Sepia(BufferedImage original) {
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                Color color = new Color(original.getRGB(x, y));
                int r = (int) (color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189);
                int g = (int) (color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168);
                int b = (int) (color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131);
                result.setRGB(x, y, new Color(clamp(r), clamp(g), clamp(b)).getRGB());
            }
        }
        return result;
    }

    private BufferedImage blur(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 1; y < image.getHeight() - 1; y++) {
            for (int x = 1; x < image.getWidth() - 1; x++) {
                int r = 0, g = 0, b = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color color = new Color(image.getRGB(x + kx, y + ky));
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                    }
                }

                result.setRGB(x, y, new Color(r / 9, g / 9, b / 9).getRGB());
            }
        }
        return result;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void resetImage(ImagePanel panel) {
        BufferedImage original = panel.getOriginalImage();
        if (original != null) {
            panel.setSelectedImage(original);
        }
    }
}