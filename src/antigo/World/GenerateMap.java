package antigo.World;

import antigo.Main.Main;
import Media.*;
import antigo.Main.KeyMap;
import antigo.Gif;
import antigo.Main.Variables;
import antigo.Media.Tiff;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GenerateMap {
    public static class GeneratedMap {
        public final KeyMap layers;
        public final List<Tile> tiles;
        public final KeyMap pixels;

        public final int width, height;

        public GeneratedMap(KeyMap l, KeyMap p, List<Tile> t, int w, int h) {
            layers = l;
            pixels = p;
            tiles = t;
            width = w;
            height = h;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Tile[] getInsideCamera(String name) {
            Rectangle cam = Camera.genScreen();
            List<Tile> inside = new ArrayList<>();

            for (Tile t : tiles) {
                if (t.getName().equals(name) || name.equals("all")) {
                    Main.log(new Rectangle(t.x, t.y, t.width, t.height), cam);

                    if (cam.intersects(t))
                        inside.add(t);

                }
            }

            System.out.println(inside.size());

            return inside.toArray(new Tile[] {});
        }
    }
    public final KeyMap layers = new KeyMap();
    public final List<Tile> tiles = new ArrayList<>();
    public final KeyMap pixels = new KeyMap();
    public final GeneratedMap map;

    /*static MultiIf i = new MultiIf();

    static {
        i.add(new Object[]{0xFF000000, "col"}, (r) -> new Tile(new String[]{"all"}, null, null));

        i.add(new Object[]{0xFF452413, "cen"}, (r) -> new Tile(new String[]{}, (Gif) Variables.texture.get("tiles.grass").getValue(), (Rectangle) r));

        i.add(new Object[]{0, "col"}, (r) -> new Tile(null, null, null));
    }*/

    public GenerateMap(Tiff tiff) {
        for (BufferedImage img : tiff.getImgs()) {
            String name = "";
            switch(img.getRGB(img.getWidth()-1, img.getHeight()-1)) {
                case 0xFF000000: {
                    name = "col";
                    break;
                }

                case 0xFF452413: {
                    name = "cen";
                    break;
                }
            }

            Main.log(name);

            layers.add(name, img);
            int[] p = new int[img.getWidth() * img.getHeight()];
            img.getRGB(0, 0, img.getWidth(), img.getHeight(), p, 0, tiff.getWidth());
            pixels.add(name, p);

            Main.log(p.length, img.getWidth(), img.getHeight());

            for (int xx = 0; xx < tiff.getWidth(); xx++) {
                for (int yy = 0; yy < tiff.getHeight(); yy++) {
                    int calculus = xx + yy * tiff.getWidth();
                    int px = p[calculus];
                    Tile t = null;
                    Rectangle pos = new Rectangle(xx * Variables.WIDTH, yy * Variables.HEIGHT, Variables.WIDTH, Variables.HEIGHT);

                    if (pos.y < 0) Main.log(pos);

                    //Object[] os = i.testAll(new Object[]{px, name}, pos);

                    /*for (Object o : os) {
                        if (o != null) {
                            t = (Tile) o;
                        }
                    }*/

                    switch (px) {
                        case 0xFF000000 -> {
                            switch (name) {
                                case "col" -> t = new Tile(new String[]{"all"}, null, pos, name);
                            }
                        }

                        case 0xFF452413 -> {
                            switch (name) {
                                case "cen" -> t = new Tile(new String[]{}, (Gif) Variables.texture.get("tiles.grass").getValue(), pos, name);
                            }
                        }

                        default -> {
                            switch (name) {
                                case "col" -> t = new Tile(null, null, pos, name);

                                case "cen" -> {
                                    t = new Tile(null, (Gif) Variables.texture.get("tiles.sky").getValue(), pos, name);
                                }
                            }
                        }
                    }

                    tiles.add(t);
                }
            }
        }

        map = new GeneratedMap(layers, pixels, tiles, tiff.getWidth(), tiff.getHeight());
    }
}
