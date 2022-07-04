package antigo.World;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

import Media.*;
import antigo.Gif;

public class Tile extends Rectangle {
    protected String[] colidesWith;
    protected Gif texture;
    protected String name;

    public Tile(String[] colidesWith, Gif texture, Rectangle pos, String name) {
        if (colidesWith == null) colidesWith = new String[]{};

        this.texture = texture;

        this.name = name;
        this.x = pos.x;
        this.y = pos.y;
        this.width = pos.width;
        this.height = pos.height;

        boolean all = false;

        for (String s : colidesWith) {
            if (Objects.equals(s, "all")) {
                all = true;
                break;
            }
        }

        if (all) colidesWith = new String[]{"all"};

        this.colidesWith = colidesWith;
    }

    public Gif getTexture() {
        return texture;
    }

    public String[] getColidesWith() {
        return colidesWith;
    }

    public void render(Graphics g) {
        Rectangle posA = this;

        posA.x -= Camera.x;
        posA.y -= Camera.y;

        if (texture != null) texture.drawAnimatedGif(g, posA);
    }

    public String getName() {
        return name;
    }

    public void tick() {}

    public boolean intersects(Rectangle r) {
        return this.intersects(r);
    }

    @Override
    public String toString() {
        return "Tile {" +
                "colidesWith=" + Arrays.toString(colidesWith) +
                ", texture=" + texture +
                ", pos=" + this +
                '}';
    }
}
