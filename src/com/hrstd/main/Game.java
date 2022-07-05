package com.hrstd.main;

import com.hrstd.events.Event;
import com.hrstd.events.EventListener;
import com.hrstd.events.EventListenerInterface;
import com.hrstd.events.exec.ExecNoDo;
import com.hrstd.events.exec.noDo.ExecGraphic;
import com.hrstd.events.start.DrawEvent;
import com.hrstd.events.start.KeybordInputEvent;
import com.hrstd.events.start.MouseClickEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Objects;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {
    @Serial
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    public static double FPS = 60, fps = 0, ffps = FPS;
    public static int DWIDTH = 240, WIDTH = DWIDTH, DHEIGHT = 160, HEIGHT = DHEIGHT, SCALE = 3;
    private static BufferedImage image;

	public Game() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        addKeyListener(this);
        addMouseListener(this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initFrame() {
        frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        EventListener.def.addListener((e, i) -> {
            if (!(e instanceof MouseClickEvent)) return;
            if (!Objects.equals(((MouseClickEvent) e).getType(), "clicked")) return;

            int x = ((MouseClickEvent) e).getX() / (frame.getWidth() / DWIDTH);
            int y = ((MouseClickEvent) e).getY() / (frame.getHeight() / DHEIGHT);

            System.out.println("x = " + x);
            System.out.println("y = " + y);

        });

        Game game = new Game();
        game.start();
    }

    public void tick() {
        WIDTH = (int) frame.getWidth() / SCALE;
        HEIGHT = (int) frame.getHeight() / SCALE;
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(255, 255, 255));
        Graphics finalG = g;
        String fpsTXT = "fps: " + fps + ", media fps: " + ffps + ", fps esperado: " + FPS;
        new ExecGraphic("drawing fps", () -> finalG.drawString(fpsTXT, 3, 10), null, finalG, new DrawEvent.Drawable(3, 10, fpsTXT));
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS;
        double delta = 0;
        double timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }


            if (System.currentTimeMillis() - timer >= 1000) {
                ffps = (int) (fps + ffps) / 2;
                fps = 0;
                timer = System.currentTimeMillis();
            }
        }

        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        EventListener.def.addEvent(new KeybordInputEvent("KeybordEvent", () -> {}, e.getKeyCode(), e.getKeyChar(), "typed"));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        EventListener.def.addEvent(new KeybordInputEvent("KeybordEvent", () -> {}, e.getKeyCode(), e.getKeyChar(), "pressed"));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        EventListener.def.addEvent(new KeybordInputEvent("KeybordEvent", () -> {}, e.getKeyCode(), e.getKeyChar(), "released"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        EventListener.def.addEvent(new MouseClickEvent("MouseEvent", () -> {}, e.getX(), e.getY(), "clicked"));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        EventListener.def.addEvent(new MouseClickEvent("MouseEvent", () -> {}, e.getX(), e.getY(), "pressed"));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        EventListener.def.addEvent(new MouseClickEvent("MouseEvent", () -> {}, e.getX(), e.getY(), "released"));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        EventListener.def.addEvent(new MouseClickEvent("MouseEvent", () -> {}, e.getX(), e.getY(), "entered"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        EventListener.def.addEvent(new MouseClickEvent("MouseEvent", () -> {}, e.getX(), e.getY(), "exited"));
    }
}
