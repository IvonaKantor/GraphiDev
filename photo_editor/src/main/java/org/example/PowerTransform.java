package org.example;
import java.awt.image.BufferedImage;

public class PowerTransform {
    public BufferedImage brighten(BufferedImage inputImg, double amount) {

        if(inputImg == null)
            return null;

        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    return outputImg;
    }

}

