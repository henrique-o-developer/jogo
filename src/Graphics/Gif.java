package Graphics;

import Graphics.Utils.ImageFrame;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gif {
    private final String path;
    private final ImageFrame[] frames;
    private final int w, h;
    private final BufferedImage[] drawableImages;
    private int frams = 0, frms = 0;

    public Gif(String path) {
        this.path = path;

        frames = getFrames();

        assert frames != null;

        h = frames[0].getHeight();
        w = frames[0].getWidth();

        drawableImages = getImagesToDraw();
    }

    private ImageFrame[] getFrames() {
        try {
            InputStream stream = new FileInputStream(path);

            ArrayList<ImageFrame> frames = new ArrayList(2);

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(ImageIO.createImageInputStream(stream));

            int lastx = 0;
            int lasty = 0;

            int width = -1;
            int height = -1;

            IIOMetadata metadata = reader.getStreamMetadata();

            Color backgroundColor = null;

            if (metadata != null) {
                IIOMetadataNode globalRoot = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());

                NodeList globalColorTable = globalRoot.getElementsByTagName("GlobalColorTable");
                NodeList globalScreeDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");

                if (globalScreeDescriptor != null && globalScreeDescriptor.getLength() > 0) {
                    IIOMetadataNode screenDescriptor = (IIOMetadataNode) globalScreeDescriptor.item(0);

                    if (screenDescriptor != null) {
                        width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
                        height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
                    }
                }

                if (globalColorTable != null && globalColorTable.getLength() > 0) {
                    IIOMetadataNode colorTable = (IIOMetadataNode) globalColorTable.item(0);

                    if (colorTable != null) {
                        String bgIndex = colorTable.getAttribute("backgroundColorIndex");

                        IIOMetadataNode colorEntry = (IIOMetadataNode) colorTable.getFirstChild();
                        while (colorEntry != null) {
                            if (colorEntry.getAttribute("index").equals(bgIndex)) {
                                int red = Integer.parseInt(colorEntry.getAttribute("red"));
                                int green = Integer.parseInt(colorEntry.getAttribute("green"));
                                int blue = Integer.parseInt(colorEntry.getAttribute("blue"));

                                backgroundColor = new Color(red, green, blue);
                                break;
                            }

                            colorEntry = (IIOMetadataNode) colorEntry.getNextSibling();
                        }
                    }
                }
            }

            BufferedImage master = null;
            boolean hasBackround = false;

            for (int frameIndex = 0; ; frameIndex++) {
                BufferedImage image;
                try {
                    image = reader.read(frameIndex);
                } catch (IndexOutOfBoundsException io) {
                    break;
                }

                if (width == -1 || height == -1) {
                    width = image.getWidth();
                    height = image.getHeight();
                }

                IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
                IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
                NodeList children = root.getChildNodes();

                int delay = Integer.valueOf(gce.getAttribute("delayTime"));

                String disposal = gce.getAttribute("disposalMethod");

                if (master == null) {
                    master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    master.createGraphics().setColor(backgroundColor);
                    master.createGraphics().fillRect(0, 0, master.getWidth(), master.getHeight());

                    hasBackround = image.getWidth() == width && image.getHeight() == height;

                    master.createGraphics().drawImage(image, 0, 0, null);
                } else {
                    int x = 0;
                    int y = 0;

                    for (int nodeIndex = 0; nodeIndex < children.getLength(); nodeIndex++) {
                        Node nodeItem = children.item(nodeIndex);

                        if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                            NamedNodeMap map = nodeItem.getAttributes();

                            x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
                            y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
                        }
                    }

                    if (disposal.equals("restoreToPrevious")) {
                        BufferedImage from = null;
                        for (int i = frameIndex - 1; i >= 0; i--) {
                            if (!frames.get(i).getDisposal().equals("restoreToPrevious") || frameIndex == 0) {
                                from = frames.get(i).getImage();
                                break;
                            }
                        }

                        {
                            ColorModel model = from.getColorModel();
                            boolean alpha = from.isAlphaPremultiplied();
                            WritableRaster raster = from.copyData(null);
                            master = new BufferedImage(model, raster, alpha, null);
                        }
                    } else if (disposal.equals("restoreToBackgroundColor") && backgroundColor != null) {
                        if (!hasBackround || frameIndex > 1) {
                            master.createGraphics().fillRect(lastx, lasty, frames.get(frameIndex - 1).getWidth(), frames.get(frameIndex - 1).getHeight());
                        }
                    }
                    master.createGraphics().drawImage(image, x, y, null);

                    lastx = x;
                    lasty = y;
                }

                {
                    BufferedImage copy;

                    {
                        ColorModel model = master.getColorModel();
                        boolean alpha = master.isAlphaPremultiplied();
                        WritableRaster raster = master.copyData(null);
                        copy = new BufferedImage(model, raster, alpha, null);
                    }
                    frames.add(new ImageFrame(copy, delay, disposal, image.getWidth(), image.getHeight()));
                }

                master.flush();
            }
            reader.dispose();

            return frames.toArray(new ImageFrame[frames.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void drawAnimatedGif(Graphics g) {
        var frmsM = frames[frams].getDelay();

        if (frms >= frmsM) {
            frms = 0;

            if (frams >= frames.length) {
                frams = 0;
            }

            drawGifFrameByIndex(frams, g);
        } else frms++;
    }

    private BufferedImage[] getImagesToDraw() {
        BufferedImage master = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        List<BufferedImage> imgs = new ArrayList();
        for (ImageFrame frame : frames) {
            BufferedImage img = frame.getImage();
            String dis = frame.getDisposal();


            if (Objects.equals(dis, "restoreToBackgroundColor")) {
                master = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            }

            Graphics g = master.getGraphics();

            g.drawImage(img, img.getTileGridXOffset(), img.getTileGridYOffset(), frame.getWidth(), frame.getHeight(), null);

            imgs.add(master);
        }

        return imgs.toArray(new BufferedImage[frames.length]);
    }

    public void drawGifFrameByIndex(int i, Graphics g) {
        BufferedImage img = drawableImages[i];

        g.drawImage(img, 0, 0, null);
    }

    @Override
    public String toString() {
        return "Gif{" +
                "path='" + path + '\'' +
                ", frames=" + Arrays.toString(frames) +
                ", w=" + w +
                ", h=" + h +
                ", drawableImages=" + Arrays.toString(drawableImages) +
                ", frams=" + frams +
                ", frms=" + frms +
                '}';
    }
}
