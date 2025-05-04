package org.example;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
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
                showError();
            }
        }
    }

    private void showError() {
        JOptionPane.showMessageDialog(null, "Error with opening an image", "Error", JOptionPane.ERROR_MESSAGE);
    }
}