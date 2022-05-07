package Main;

import Events.*;
import File.*;
import Graphics.*;
import Events.Inline.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.metadata.IIOMetadataNode;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    public static double FPS = 60, fps = 0, ffps = FPS;
    public static int WIDTH = 240, HEIGHT = 160, SCALE = 3;
    BufferedImage image;
    public static Main obj;

    public Main() {
        obj = this;

        new InitInlineEvent("setting size of window", null, 0, false, false);

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        new InitInlineEvent("calling method initFrame()", null, 0, false, false);

        initFrame();

        new InitInlineEvent("setting image", null, 0, false, false);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public synchronized void start() {
        new InitInlineEvent("setting thread", null, 0, false, false);

        thread = new Thread(Main.obj);

        new InitInlineEvent("starting thread", null, 0, false, false);

        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        new InitInlineEvent("stopping thread", null, 0, false, false);

        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initFrame() {
        new InitInlineEvent("initializing jframe", null, 0, false, false);

        frame = new JFrame();
        frame.add(Main.obj);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new InitInlineEvent("starting game", null, 0, false, false);

        Main game = new Main();
        game.start();
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
                return((IIOMetadataNode) rootNode.item(i));
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }

    public void tick() {
        new TickInlineEvent("redefining metrics", null, 0, false, false);

        WIDTH = frame.getWidth() / SCALE;
        HEIGHT = frame.getHeight() / SCALE;
    }

    public void render() {
        new InitInlineEvent("checking buffer strategy", null, 0, false, false);

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            Main.obj.createBufferStrategy(3);
            return;
        }

        new RenderInlineEvent("getting graphics", null, 0, false, false);

        Graphics g = image.getGraphics();

        Spritesheet.playerWalking.getGif().drawAnimatedGif(g);

        new RenderInlineEvent("getting defined graphics", null, 0, false, false);
        g = bs.getDrawGraphics();
        new RenderInlineEvent("drawing image", null, 0, false, false);
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        new RenderInlineEvent("writing fps", null, 0, false, false);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(255, 255, 255));
        g.drawString("fps: " + fps + ", media fps: " + ffps + ", fps esperado: " + FPS, 3, 10);
        new RenderInlineEvent("showing", null, 0, false, false);
        bs.show();
    }

    public void run() {
        new RunInlineEvent("decompiling jar", null, 0, false, false);

        if (Variables.isExecutingInJar()) {
            Variables.decompileJar();
        }

        new RunInlineEvent("setting Sprite sheet vars", null, 0, false, false);

        Spritesheet.setVars();

        new RunInlineEvent("setting vars", null, 0, false, false);

        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS;
        double delta = 0;
        double timer = System.currentTimeMillis();
        new RunInlineEvent("running while", null, 0, false, false);

        while (isRunning) {
            new RunInlineEvent("computing fps", null, 0, false, false);

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                new RunInlineEvent("ticking and rendering", null, 0, false, false);
                tick();
                render();
                fps++;
                delta--;
            }


            if (System.currentTimeMillis() - timer >= 1000) {
                new RunInlineEvent("printing fps", null, 0, false, false);

                System.out.println("fps: "+ fps);
                ffps = (ffps + fps) / 2;
                fps = 0;
                timer = System.currentTimeMillis();
            }
        }

        stop();
    }

    public static void setTimeout(Runnable r, int time) {
        Timer timer = new Timer();

        timer.schedule((TimerTask) r, time);
    }

    public static void setInterval(Runnable r, int time) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate((TimerTask) r, time, time);
    }

    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
}