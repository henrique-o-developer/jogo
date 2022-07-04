package com.hrstd.main;

import com.hrstd.events.Event;
import com.hrstd.events.EventListener;
import com.hrstd.events.EventListenerInterface;
import com.hrstd.events.exec.ExecNoDo;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    public static double FPS = 60, fps = 0, ffps = FPS;
    public static int WIDTH = 240, HEIGHT = 160, SCALE = 3;
    private BufferedImage image;

	public Game() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
        //EventListener.def.addListener(e -> e.setCanselled());

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

        if (image.getWidth() != WIDTH || image.getHeight() != HEIGHT) {
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        }

        Graphics g = image.getGraphics();
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(255, 255, 255));
        Graphics finalG = g;
        new ExecNoDo("drawing fps", () -> finalG.drawString("fps: " + fps + ", media fps: " + ffps + ", fps esperado: " + FPS, 3, 10), null);
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
                ffps = Integer.parseInt(String.valueOf((fps + ffps) / 2));
                fps = 0;
                timer = System.currentTimeMillis();
            }
        }

        stop();
    }
}
