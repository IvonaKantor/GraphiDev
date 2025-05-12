package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage firstImage;
    private BufferedImage secondImage;
    private double scale = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;
    private Point dragStart;
    private int selectedImage = 1;

    public ImagePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
                selectImageAtPoint(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart != null) {
                    Point current = e.getPoint();
                    offsetX += current.x - dragStart.x;
                    offsetY += current.y - dragStart.y;
                    dragStart = current;
                    repaint();
                }
            }
        });

        addMouseWheelListener(e -> {
            double scaleFactor = e.getWheelRotation() < 0 ? 1.1 : 0.9;
            scale *= scaleFactor;
            repaint();
        });
    }

}