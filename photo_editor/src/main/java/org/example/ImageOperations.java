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

    private BufferedImage blur(BufferedImage image) {

    }


    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}