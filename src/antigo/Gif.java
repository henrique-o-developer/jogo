package antigo;

import antigo.Media.Utils.ImageFrame;
import org.w3c.dom.NodeList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class Gif {
    private final String path;
    private Audio audio;
    private final ImageFrame[] frames;
    private final int w, h;
    private final BufferedImage[] drawableImages;
    private int frams = 0, frms = 0;

    private final HashMap<String, String> props;

    public Gif(String path, HashMap<String, String> props) {
        this.path = path;
        this.props = props == null ? new HashMap<>() : props;

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

                int delay = Integer.valueOf(gce.getAttribute("delayTime"));

                String disposal = gce.getAttribute("disposalMethod");

                frames.add(new ImageFrame(image, delay, disposal, image.getWidth(), image.getHeight(), backgroundColor));
            }

            reader.dispose();

            return frames.toArray(new ImageFrame[frames.size()]);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return null;
    }

    public void resetAnimation() {
        frms = 0;
        frams = 0;
    }
    public void drawAnimatedGif(Graphics g, int x, int y, int w, int h) {
        drawAnimatedGif(g, new Rectangle(x, y, w, h));
    }
    public void drawAnimatedGif(Graphics g, Rectangle r) {
        //try {
        if (frames.length <= frams) return;

        int frmsM = frames[frams].getDelay();

        if (frms >= frmsM) {
            frms = 0;
            frams++;

            int max = -1;

            try {
                max = Integer.parseInt(props.get("stopGifAtIndex"));

                if (max >= frames.length) max = frames.length;
                if (max < 0) max = 0;
            } catch (Exception e) {
                //e.printStackTrace();
            }

            if (max == -1) max = frames.length;

            if (frams >= max) {
                int restart = -1;

                try {
                    restart = Integer.parseInt(props.get("restartGifAtIndex"));

                    if (restart >= frames.length) restart = 0;
                    if (restart < 0) restart = 0;
                    if (restart > max) restart = max;
                } catch (Exception e) {
                    //e.printStackTrace();
                }

                if (restart == -1) restart = 0;

                frams = restart;
            }
        } else frms++;

        drawGifFrameByIndex(frams, g, r);
        //} catch (Exception ignored) {}
    }

    private BufferedImage[] getImagesToDraw() {
        BufferedImage master = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        List<BufferedImage> imgs = new ArrayList();
        boolean dbgc = Objects.equals(props.get("drawBGColor"), "true");

        for (ImageFrame frame : frames) {
            BufferedImage img = frame.getImage();
            String dis = frame.getDisposal();


            if (Objects.equals(dis, "restoreToBackgroundColor")) {
                master = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g = (Graphics2D) master.getGraphics();

                if (dbgc) {
                    g.setColor(frame.getBack());
                    g.fillRect(0, 0, master.getWidth(), master.getHeight());
                }
            }

            Graphics2D g = (Graphics2D) master.getGraphics();

            g.drawImage(img, img.getTileGridXOffset(), img.getTileGridYOffset(), frame.getWidth(), frame.getHeight(), null);

            imgs.add(master);
        }

        return imgs.toArray(new BufferedImage[frames.length]);
    }

    public void drawGifFrameByIndex(int i, Graphics g, int x, int y, int w, int h) {
        drawGifFrameByIndex(i, g, new Rectangle(x, y, w, h));
    }
    public void drawGifFrameByIndex(int i, Graphics g, Rectangle r) {
        if (r == null) return;

        BufferedImage img = drawableImages[i];

        g.drawImage(img, r.x, r.y, r.width, r.height, null);

        if (audio == null) return;
        if (audio.getProps() == null) return;
        if (audio.getProps().get("playOnFrames") == null) return;

        String[] S = audio.getProps().get("playOnFrames").replaceAll(" ", "").split(",");

        for (String s : S) {
            try {
                int frame = Integer.parseInt(s);

                if (i == frame) audio.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getW() {
        return w;
    }

    public int getF() {
        return frams;
    }

    public int getH() {
        return h;
    }

    public Gif flip() {

        Gif g = new Gif(this.path, this.props);

        for (int i = 0; i < g.drawableImages.length; i++) {
            g.drawableImages[i] = Spritesheet.flipImage(g.drawableImages[i]);
        }

        for (int i = 0; i < g.frames.length; i++) {
            ImageFrame f = g.frames[i];
            g.frames[i] = new ImageFrame(Spritesheet.flipImage(f.getImage()), f.getDelay(), f.getDisposal(), f.getWidth(), f.getHeight(), f.getBack());
        }

        return g;
    }

    public Audio getAudio() {
        return audio;
    }

    public void attAudio(Audio audio) {
        if (audio == null) return;

        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Gif {" +
                "path='" + path + '\'' +
                ", frames=" + Arrays.toString(frames) +
                ", w=" + w +
                ", h=" + h +
            '}';
    }
}
