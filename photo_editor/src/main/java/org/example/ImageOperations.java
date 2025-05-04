package org.example;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageOperations {

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
                showError("Błąd wczytywania obrazu");
            }
        }
    }
}