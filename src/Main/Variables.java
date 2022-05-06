package Main;

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

    public static boolean isExecutingInJar() {
        return String.valueOf(Main.class.getProtectionDomain().getCodeSource().getLocation().toString()).endsWith(".jar");
    }

    public static void decompileJar() {
        try {
            DECOMPILING = true;

            new Thread(() -> {
                while (DECOMPILING) {
                    //System.out.println("a");
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

                    while (is.available() > 0) {
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
