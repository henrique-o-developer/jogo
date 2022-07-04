package antigo.Media;

import antigo.Spritesheet;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Tiff {
    private final List<BufferedImage> imgs = new ArrayList<>();
    private final HashMap<String, String> props;
    private String path;

    public Tiff(String path, HashMap<String, String> props) {
        this.path = path;
        this.props = props == null ? new HashMap<>() : props;
        try {
            ImageInputStream is = ImageIO.createImageInputStream(new java.io.File(path));

            if (is == null || is.length() == 0) {
                throw new IOException("ImageInputStream is null");
            }

            Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);

            if (iterator == null || !iterator.hasNext()) {
                throw new IOException("Image file format not supported by ImageIO: " + path);
            }

            var reader = iterator.next();

            reader.setInput(is);

            for (int i = 0; i < reader.getNumImages(true); i++) {
                BufferedImage img = reader.read(i);

                boolean equal = false;

                for (int i1 = 0; i1 < imgs.size(); i1++) {
                    BufferedImage im = imgs.get(i1);

                    if (img.getRGB(img.getWidth()-1, img.getHeight()-1)
                            == im.getRGB(im.getWidth()-1, im.getHeight()-1)) {
                        Graphics g = im.getGraphics();

                        g.drawImage(img, 0, 0, im.getWidth(), im.getHeight(), null);

                        imgs.set(i, im);

                        equal = true;
                    }
                }

                if (!equal) {
                    imgs.add(img);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BufferedImage> getImgs() {
        return imgs;
    }

    public HashMap<String, String> getProps() {
        return props;
    }

    public int getWidth() {
        return getImgs().get(0).getWidth();
    }

    public int getHeight() {
        return getImgs().get(0).getHeight();
    }

    public Tiff flip() {

        Tiff t = new Tiff(this.path, this.props);

        for (int i = 0; i < t.imgs.size(); i++) {
            t.imgs.set(i, Spritesheet.flipImage(t.imgs.get(i)));
        }

        return t;
    }
}
