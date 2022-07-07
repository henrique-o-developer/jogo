package com.hrstd.components;

import com.hrstd.components.file.Gif;
import com.hrstd.components.file.TextureGetter;
import com.hrstd.events.start.StandardEvent;
import com.hrstd.main.Game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Variables {
    public static String
        GAME_NAME = "GAME",
        USER_HOME = System.getProperty("user.home"),
        GAME_HOME = USER_HOME + File.separator + "." + GAME_NAME,
        SAVE_FILE = GAME_HOME + File.separator + "save.txt",
        DECOMP_DIR = GAME_HOME + File.separator + "game_files",
        ACTUAL_TEXTURE = "default",
        TEXTURES_DIR = DECOMP_DIR + File.separator + "graphics" + File.separator + "textures" + File.separator + ACTUAL_TEXTURE,
        MAPS_DIR = DECOMP_DIR + File.separator + "graphics" + File.separator + "maps"
    ;

    public static int
        WIDTH = 32,
        HEIGHT = 32
    ;

    /*public static GenerateMap.GeneratedMap map;
    public static MapManipulator map_manip;

    public static void setMap(String mapName) {
        map = new GenerateMap(new TiffGetter(new File(MAPS_DIR+"/"+mapName+".tiff")).getTiff()).map;
        map_manip = new MapManipulator(map);
    }*/

    public static void changeTexture(String texture) {
        ACTUAL_TEXTURE = texture;
        TEXTURES_DIR = DECOMP_DIR + File.separator + "graphics" + File.separator + "textures" + File.separator + ACTUAL_TEXTURE;
        setTextures();
    }
    public static Key
        U = Key.getByCode(38),
        D = Key.getByCode(40),
        L = Key.getByCode(37),
        R = Key.getByCode(39)
    ;

    public static TextureGetter texture = new TextureGetter();

    static {
        DECOMP_DIR = isExecutingInJar()?DECOMP_DIR:"res";
    }
    public static boolean DECOMPILING = false;
    public static String decomplingName;
    public static int Byte;
    public static int from;

    public static boolean isExecutingInJar() {
        return String.valueOf(Game.class.getProtectionDomain().getCodeSource().getLocation().toString()).endsWith(".jar");
    }

    public static void setTextures() {
        texture.setAll(TEXTURES_DIR);
    }

    public static void DrawDefined(toDraw draw) {
        new StandardEvent("checking buffer strategy");

        BufferStrategy bs = Game.obj.getBufferStrategy();

        if (bs == null) {
            Game.obj.createBufferStrategy(3);

            return;
        }
        new StandardEvent("getting defined graphics");
        Graphics g = bs.getDrawGraphics();

        new StandardEvent("executing draw");
        draw.run(g);

        new StandardEvent("showing");
        bs.show();
    }

    public static void DrawDefaultBackground(Graphics g) {
        new StandardEvent("cleaning screen");

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        g.setColor(Color.WHITE);

        (((Gif) texture.get("misc.background").getValue()).drawAnimatedGif(g, 0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE)).registerEvent();
    }

    public static void decompileJar() {
        /*if (DECOMPILING) return;

        DECOMPILING = true;

        new Thread(() -> {
            while (DECOMPILING) {

            Main.drawCenteredString(g, "decompiling file " + decomplingName, new Rectangle(0, -25, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE), new Font("arial", Font.BOLD, 20));
            Main.drawCenteredString(g, Byte + "/" + from, new Rectangle(0, 25, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE), new Font("arial", Font.BOLD, 30));

            new RenderEvent("showing", null);

            new InitEvent("setting Spritesheet vars", null);

            Variables.setTextures();

            new InitEvent("creating player", null);

            Main.entities.add(new Player(10, 10, Variables.WIDTH, Variables.HEIGHT, (Gif) Variables.texture.get("player.player-walking").getValue()));

            new InitEvent("setting vars", null);

            Variables.setMap("initial");
        }).start();

        new Thread(() -> {
            try {
                JarFile jar = new JarFile(new File(String.valueOf(Main.class.getProtectionDomain().getCodeSource().getLocation())).getPath().split(":", 2)[1]);
                Enumeration<JarEntry> enumEntries = jar.entries();
                while (enumEntries.hasMoreElements()) {
                    JarEntry file = enumEntries.nextElement();
                    File f = new File(Variables.DECOMP_DIR + java.io.File.separator + file.getName());

                    if (file.getName().startsWith("graphics/")) {
                        if (file.isDirectory()) {
                            f.mkdirs();
                            continue;
                        } else {
                            new File(f.getParent()).mkdirs();
                        }

                        InputStream is = jar.getInputStream(file);
                        FileOutputStream fos = new java.io.FileOutputStream(f);
                        decomplingName = file.getName();
                        from = is.available();

                        while (is.available() > 0) {
                            Byte = is.available() - 1;
                            fos.write(is.read());
                        }

                        fos.close();
                        is.close();
                    }
                }

                jar.close();

                DECOMPILING = false;
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();*/
    }

    public interface toDraw {
        void run(Graphics g);
    }
}
