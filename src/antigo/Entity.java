package antigo;

import Media.*;
import antigo.World.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected int x, y, w, h;
    protected Gif gif;
    protected final Gif originalGif, originalGifFlipped;
    protected BufferedImage fullImage;
    protected double vy = 0;


    public Entity(int x, int y, int w, int h, Gif gif) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.gif = gif;
        this.originalGif = gif;
        this.originalGifFlipped = gif.flip();

        this.fullImage = new BufferedImage(gif.getW(), getH(), BufferedImage.TYPE_INT_ARGB);
    }

    public void render(Graphics g2) {
        Graphics2D g = (Graphics2D) fullImage.getGraphics();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g.fillRect(0, 0, fullImage.getWidth(), fullImage.getHeight());

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        gif.drawAnimatedGif(g, 0, 0, fullImage.getWidth(), fullImage.getHeight());

        g2.drawImage(fullImage, x - Camera.x, y - Camera.y, w, h, null);
    }

    public void tick() {
        y = (int) (y + vy);

        if (y < 100) {
            vy += 0.1;
        } else {
            vy = 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
