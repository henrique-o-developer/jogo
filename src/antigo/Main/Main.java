package antigo.Main;

import Entities.*;
import Entities.Player.*;
import Errors.*;
import Events.Interfaces.*;
import Events.*;
import Media.*;
import antigo.*;
import antigo.Entities.Player.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
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
    public static long allFps = 0;
    public static int IWIDTH = 240, WIDTH = IWIDTH, IHEIGHT = 160, HEIGHT = IHEIGHT, SCALE = 3;
    public static BufferedImage image;
    public static Main obj;
    public static List<Entity> entities = new ArrayList<>();

    public Main() {
        obj = this;

        new InitEvent("setting size of window", null);

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        new InitEvent("calling method initFrame()", null);

        initFrame();

        new InitEvent("setting image", null);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        this.addKeyListener(this);
    }

    public synchronized void start() {
        new InitEvent("setting thread", null);

        thread = new Thread(Main.obj);

        new InitEvent("starting thread", null);

        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        new InitEvent("stopping thread", null);

        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initFrame() {
        new InitEvent("initializing jframe", null);

        frame = new JFrame();
        frame.add(Main.obj);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new InitEvent("starting game", null);

        Main game = new Main();
        game.start();

        //new Audio("/home/henrique/Documents/pro/java/GAME/res/graphics/textures/default/player/player-walking/player-walking.wav", null).play();
    }

    public void tick() {
        new TickEvent("redefining metrics", null);

        WIDTH = frame.getWidth() / SCALE;
        HEIGHT = frame.getHeight() / SCALE;

        new TickEvent("ticking entities", new CancelInterface() {
            @Override
            public int maxTimeToCancelInMills() {
                return 1;
            }

            @Override
            public void run() {
                for (Entity entitye : entities) {
                    if (entitye instanceof Player) entitye.tick();
                }
            }
        });

        new TickEvent("ticking entities", new CancelInterface() {
            @Override
            public int maxTimeToCancelInMills() {
                return 1;
            }

            @Override
            public void run() {
                for (Entity entitye : entities) {
                    if (!(entitye instanceof Player)) entitye.tick();
                }
            }
        });
    }

    public void render() {
        new InitEvent("checking buffer strategy", null);

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            Main.obj.createBufferStrategy(3);
            return;
        }

        new RenderEvent("getting graphics", null);

        Graphics g = image.getGraphics();

        new RenderEvent("cleaning screen", null);

        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Graphics finalG = g;

        new RenderEvent("drawing map", new CancelInterface() {
            @Override
            public int maxTimeToCancelInMills() {
                return 1;
            }

            @Override
            public void run() {
                Variables.map_manip.render(finalG);
            }
        });


        new RenderEvent("drawing entities", null);

        for (Entity entitye : entities) {
            if (!(entitye instanceof Player)) {
                new RenderEvent("drawing entity " + entitye, new CancelInterface() {
                    @Override
                    public int maxTimeToCancelInMills() {
                        return 1;
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
                        entitye.render(finalG);
                    }
                });
            }
        }

        for (Entity entitye : entities) {
            if (entitye instanceof Player) {
                new RenderEvent("drawing player " + entitye, new CancelInterface() {
                    @Override
                    public int maxTimeToCancelInMills() {
                        return 1;
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
                        entitye.render(finalG);
                    }
                });
            }
        }

        new RenderEvent("getting defined graphics", null);
        g = bs.getDrawGraphics();
        new RenderEvent("drawing image", null);
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        new RenderEvent("writing fps", null);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(255, 255, 255));
        g.drawString("fps: " + fps + ", media fps: " + ffps + ", fps esperado: " + FPS, 3, 10);
        new RenderEvent("showing", null);
        bs.show();
    }

    public Success updateController(String what) {
        if (what.startsWith("player.emmitsKey")) {
            String[] listArr = what.split("\\.");
            String l = listArr[listArr.length - 2];

            if (listArr.length == 4) {
                new PlayerMoveEvent("player trying to emmit key: " + l, new CancelInterface() {
                    public int maxTimeToCancelInMills() {
                        return 2;
                    }

                    @Override
                    public void run() {
                        Key k = Key.getByKey(l);

                        for (Entity entity : entities) {
                            if (entity instanceof Player) {
                                if (Variables.U.equals(k)) {
                                    ((Player) entity).move("u", Boolean.parseBoolean(listArr[listArr.length - 1]));
                                }
                                if (Variables.L.equals(k)) {
                                    ((Player) entity).move("l", Boolean.parseBoolean(listArr[listArr.length - 1]));
                                } else if (Variables.R.equals(k)) {
                                    ((Player) entity).move("r", Boolean.parseBoolean(listArr[listArr.length - 1]));
                                }
                            }
                        }
                    }
                });

                return new Success("PlayerEmiitKeyController");
            } else {
                return new InputError("this input: \"" + what + "\" is out of syntax");
            }
        }

        return new Undifferentiable();
    }

    public void run() {
        new InitEvent("decompiling jar", null);

        if (Variables.isExecutingInJar()) {
            Variables.decompileJar();
        } else {
            new InitEvent("setting Spritesheet vars", null);

            Variables.setTextures();

            new InitEvent("creating player", null);

            entities.add(new Player(10, 10, Variables.WIDTH, Variables.HEIGHT, (Gif) Variables.texture.get("player.player-walking").getValue()));

            new InitEvent("setting vars", null);

            Variables.setMap("initial");
        }

        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS;
        double delta = 0;
        double timer = System.currentTimeMillis();

        new RunEvent("requesting focus", new CancelInterface() {

            @Override
            public int maxTimeToCancelInMills() {
                return 10;
            }

            @Override
            public void run() {
                Main.obj.requestFocus();
            }
        });

        new InitEvent("running while", null);

        while (isRunning) {
            if (Variables.DECOMPILING) continue;

            new InitEvent("computing fps", null);

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                new InitEvent("ticking and rendering", null);
                tick();
                render();
                fps++;
                allFps += 1;
                delta--;
            }


            if (System.currentTimeMillis() - timer >= 1000) {
                new InitEvent("printing fps", null);

                ffps = (int) ((ffps + fps) / 2);
                fps = 0;
                timer = System.currentTimeMillis();
            }
        }

        stop();
    }

    public static void setTimeout(Runnable r, int mili) {
        new Thread(() -> {
            long f = System.currentTimeMillis();

            while (true) {
                if (f - System.currentTimeMillis() <= mili) {
                    r.run();
                    break;
                }
            }
        }).start();
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

        int mvX = 0;
        int mvY = 0;

        for (Entity entity : entities) {
            if (entity instanceof Player) {
                mvX = ((Player) entity).getMvX();
                mvY = ((Player) entity).getMvY();
                break;
            }
        }

        if (
                (k.equals(Variables.L) && mvX != -1) ||
                (k.equals(Variables.R) && mvX != 1) ||
                (k.equals(Variables.U) && mvY != -1)
        ) {
            updateController("player.emmitsKey."+k.getKey()+".false");

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Key k = Key.getByCode(keyEvent.getKeyCode());

        updateController("player.emmitsKey."+k.getKey()+".true");
    }

    public static void log(Object... print) {
        String s = "";

        for (Object o : print) {
            s += (o + ", ");
        }

        s = s.substring(0, s.length()-2);

        System.out.println(s);
    }
}