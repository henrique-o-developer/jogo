package Graphics;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tiff {
    private final List<BufferedImage> imgs = new ArrayList<>();

    public Tiff(String path) {
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

                for (int i1 = 0; i1 < imgs.size(); i1++) {
                    BufferedImage im = imgs.get(i1);

                    if (img.getRGB(img.getWidth(), img.getHeight()) == im.getRGB(img.getWidth(), img.getHeight())) {
                        Graphics g = im.getGraphics();

                        g.drawImage(img, 0, 0, null);

                        imgs.set(i, im);
                    } else {
                        imgs.add(img);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BufferedImage> getImgs() {
        return imgs;
    }
}
