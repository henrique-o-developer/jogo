package Main;

import Events.Inline.*;
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
        GAME_HOME = USER_HOME + java.io.File.separator + "." + GAME_NAME,
        SAVE_FILE = GAME_HOME + java.io.File.separator + "save.txt",
        DECOMP_DIR = GAME_HOME + java.io.File.separator + "game_files"
    ;

    static {
        DECOMP_DIR = isExecutingInJar()?DECOMP_DIR:"res";
    }
    public static boolean DECOMPILING = false;
    public static String decomplingName;
    public static int Byte;
    public static int from;

    public static boolean isExecutingInJar() {
        return String.valueOf(Main.class.getProtectionDomain().getCodeSource().getLocation().toString()).endsWith(".jar");
    }

    public static void decompileJar() {
        if (DECOMPILING) return;

        try {
            DECOMPILING = true;

            new Thread(() -> {
                while (DECOMPILING) {
                    System.out.println("a");
                    new InitInlineEvent("checking buffer strategy", null, 0, false, false);

                    BufferStrategy bs = Main.obj.getBufferStrategy();

                    if (bs == null) {
                        Main.obj.createBufferStrategy(3);
                    } else {
                        new RenderInlineEvent("getting defined graphics", null, 0, false, false);
                        Graphics g = bs.getDrawGraphics();

                        new RenderInlineEvent("drawing texts", null, 0, false, false);
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE);
                        g.setColor(Color.WHITE);
                        Main.drawCenteredString(g, "decompiling file " + decomplingName, new Rectangle(0, 0 - 25, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE), new Font("arial", Font.BOLD, 20));
                        Main.drawCenteredString(g, Byte + "/" + from, new Rectangle(0, 0 + 25, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE), new Font("arial", Font.BOLD, 30));

                        new RenderInlineEvent("showing", null, 0, false, false);
                        bs.show();
                    }
                }
            }).start();

            JarFile jar = new JarFile(new File(String.valueOf(Main.class.getProtectionDomain().getCodeSource().getLocation())).getPath().split(":", 2)[1]);
            Enumeration<JarEntry> enumEntries = jar.entries();
            while (enumEntries.hasMoreElements()) {
                JarEntry file = enumEntries.nextElement();
                File f = new File(Variables.DECOMP_DIR + java.io.File.separator + file.getName());

                System.out.println(file.getRealName());
                System.out.println(file.getName());

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
                        Byte = is.available()-1;
                        fos.write(is.read());
                    }

                    fos.close();
                    is.close();
                }
            }

            jar.close();

            DECOMPILING = false;
        } catch (Exception ignored) {
            DECOMPILING = false;
        }
    }
}
