package org.example;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageGUI gui = new ImageGUI();
            gui.setVisible(true);
        });
    }
}
