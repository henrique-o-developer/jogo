package com.hrstd.components.file.utils;

import com.hrstd.components.file.Spritesheet;

import java.awt.*;

import java.awt.image.BufferedImage;

public class ImageFrame {
    private final int delay;
    private final BufferedImage image;
    private final String disposal;
    private final int width, height;
    private final Color b;

    public ImageFrame(BufferedImage image, int delay, String disposal, int width, int height, Color b){
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        this.width = width;
        this.height = height;
        this.b = b;
    }

    public ImageFrame(BufferedImage image){
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        this.width = -1;
        this.height = -1;
        this.b = null;
    }

    public Color getBack() {
        return b;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getDelay() {
        return delay;
    }

    public String getDisposal() {
        return disposal;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImageFrame flip() {
        return new ImageFrame(Spritesheet.flipImage(image), delay, disposal, width, height, b);
    }

    @Override
    public String toString() {
        return "ImageFrame {" +
                "delay=" + delay +
                ", image=" + image +
                ", disposal='" + disposal + '\'' +
                ", width=" + width +
                ", height=" + height +
                "backgrondColor: "+ b +
                '}';
    }
}