package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage firstImage;
    private BufferedImage secondImage;
    private BufferedImage firstImageOriginal;
    private BufferedImage secondImageOriginal;
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

    private void selectImageAtPoint(Point point) {
        if (firstImage == null && secondImage == null) return;

        int width = getWidth();
        int gap = 20;

        if (firstImage != null && secondImage != null) {
            int img1Width = (int) (firstImage.getWidth() * scale);
            int totalWidth = img1Width + gap + (int) (secondImage.getWidth() * scale);
            int startX = (width - totalWidth) / 2 + offsetX;

            if (point.x >= startX && point.x < startX + img1Width) {
                selectedImage = 1;
            } else if (point.x >= startX + img1Width + gap) {
                selectedImage = 2;
            }
        } else if (firstImage != null) {
            selectedImage = 1;
        } else {
            selectedImage = 2;
        }
    }

    public BufferedImage getSelectedImage() {
        return selectedImage == 1 ? firstImage : secondImage;
    }

    public int getSelectedImageNumber() {
        return selectedImage;
    }

    public void setSelectedImage(BufferedImage image) {
        if (selectedImage == 1) {
            setFirstImage(image);
        } else {
            setSecondImage(image);
        }
        repaint();
    }

    public void setFirstImage(BufferedImage image) {
        this.firstImageOriginal = image != null ? ImageTransforms.copyImage(image) : null;
        this.firstImage = image != null ? resize(image) : null;
        resetView();
    }

    public void setSecondImage(BufferedImage image) {
        this.secondImageOriginal = image != null ? ImageTransforms.copyImage(image) : null;
        this.secondImage = image != null ? resize(image) : null;
        resetView();
    }

    public BufferedImage getOriginalImage() {
        return selectedImage == 1 ? firstImageOriginal : secondImageOriginal;
    }

    private BufferedImage resize(BufferedImage original) {
        int maxWidth = getWidth() / 2 - 20;
        int maxHeight = getHeight() - 40;

        double widthRatio = (double) maxWidth / original.getWidth();
        double heightRatio = (double) maxHeight / original.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (original.getWidth() * ratio);
        int newHeight = (int) (original.getHeight() * ratio);

        BufferedImage resized = new BufferedImage(newWidth, newHeight, original.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resized;
    }

    public BufferedImage getFirstImage() {
        return firstImage;
    }

    public BufferedImage getSecondImage() {
        return secondImage;
    }

    public void resetView() {
        scale = 1.0;
        offsetX = 0;
        offsetY = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (firstImage != null || secondImage != null) {
            int width = getWidth();
            int height = getHeight();
            int gap = 20;

            if (firstImage != null && secondImage != null) {
                int img1Width = (int) (firstImage.getWidth() * scale);
                int img1Height = (int) (firstImage.getHeight() * scale);
                int img2Width = (int) (secondImage.getWidth() * scale);
                int img2Height = (int) (secondImage.getHeight() * scale);

                int totalWidth = img1Width + gap + img2Width;
                int maxHeight = Math.max(img1Height, img2Height);

                int x = (width - totalWidth) / 2 + offsetX;
                int y = (height - maxHeight) / 2 + offsetY;

                g2d.drawImage(firstImage, x, y, img1Width, img1Height, this);
                g2d.drawImage(secondImage, x + img1Width + gap, y, img2Width, img2Height, this);

                g2d.setColor(Color.RED);
                g2d.drawRect(x, y, img1Width, img1Height);
                g2d.drawRect(x + img1Width + gap, y, img2Width, img2Height);
            } else {
                BufferedImage img = firstImage != null ? firstImage : secondImage;
                int imgWidth = (int) (img.getWidth() * scale);
                int imgHeight = (int) (img.getHeight() * scale);

                int x = (width - imgWidth) / 2 + offsetX;
                int y = (height - imgHeight) / 2 + offsetY;

                g2d.drawImage(img, x, y, imgWidth, imgHeight, this);

                g2d.setColor(Color.RED);
                g2d.drawRect(x, y, imgWidth, imgHeight);
            }
        }
    }
}