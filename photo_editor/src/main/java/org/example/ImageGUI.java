package org.example;

import javax.swing.*;
import java.awt.*;

public class ImageGUI extends JFrame {
    public ImageGUI() {
        setTitle("Photo Editor");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageOperations imageOperations = new ImageOperations();
        ImagePanel imagePanel = new ImagePanel();
        ControlPanel controlPanel = new ControlPanel(imageOperations, imagePanel);

        add(controlPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setPreferredSize(new Dimension(1200, 700));
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }
}