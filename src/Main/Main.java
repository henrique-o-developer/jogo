package Main;

import Entities.Entity;
import Entities.Player.Player;
import Errors.Success;
import Events.Interfaces.CancelInterface;
import Events.RunEvent;
import Graphics.*;
import Events.Inline.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    public static double FPS = 60, fps = 0, ffps = FPS;
    public static int WIDTH = 240, HEIGHT = 160, SCALE = 3;
    BufferedImage image;
    public static Main obj;
    public static List<Entity> entities = new ArrayList<>();

    public Main() {
        obj = this;

        new InitInlineEvent("setting size of window", null, 0, false, false);

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        new InitInlineEvent("calling method initFrame()", null, 0, false, false);

        initFrame();

        new InitInlineEvent("setting image", null, 0, false, false);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        this.addKeyListener(this);
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

    public void tick() {
        new TickInlineEvent("redefining metrics", null, 0, false, false);

        WIDTH = frame.getWidth() / SCALE;
        HEIGHT = frame.getHeight() / SCALE;

        new TickInlineEvent("ticking entities", null, 0, false, false);

        for (Entity entitye : entities) {
            if (!(entitye instanceof Player)) entitye.tick();
        }

        for (Entity entitye : entities) {
            if (entitye instanceof Player) entitye.tick();
        }
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

        new RenderInlineEvent("cleaning screen", null, 0, false, false);

        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        new RenderInlineEvent("drawing entities", null, 0, false, false);

        for (Entity entitye : entities) {
            if (!(entitye instanceof Player)) entitye.render(g);
        }

        for (Entity entitye : entities) {
            if (entitye instanceof Player) entitye.render(g);
        }

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

        new RunInlineEvent("setting Spritesheet vars", null, 0, false, false);

        Spritesheet.setVars();

        new RunInlineEvent("creating player", null, 0, false, false);

        entities.add(new Player(10, 10, 32, 32, Spritesheet.playerWalking.getGif()));

        new RunInlineEvent("setting vars", null, 0, false, false);

        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS;
        double delta = 0;
        double timer = System.currentTimeMillis();

        new RunEvent("requesting focus", new CancelInterface() {
            @Override
            public Success cancel() {
                return null;
            }

            @Override
            public int maxTimeToCancelInMills() {
                return 10;
            }

            @Override
            public boolean hasMaxTimeToCancel() {
                return true;
            }

            @Override
            public boolean isCancelable() {
                return true;
            }

            @Override
            public void run() {
                Main.obj.requestFocus();
            }
        });

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

                ffps = (int) ((ffps + fps) / 2);
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        System.out.println(keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Key k = Key.getByCode(keyEvent.getKeyCode());

        for (Entity entity : entities) {
            if (entity instanceof Player) {
                if (Variables.U.equals(k)) {
                    ((Player) entity).move("u", false);
                }
                if (Variables.L.equals(k)) {
                    ((Player) entity).move("l", false);
                } else if (Variables.R.equals(k)) {
                    ((Player) entity).move("r", false);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Key k = Key.getByCode(keyEvent.getKeyCode());

        for (Entity entity : entities) {
            if (entity instanceof Player) {
                if (Variables.U.equals(k)) {
                    ((Player) entity).move("u", true);
                }
                if (Variables.L.equals(k)) {
                    ((Player) entity).move("l", true);
                } else if (Variables.R.equals(k)) {
                    ((Player) entity).move("r", true);
                }
            }
        }
    }
}