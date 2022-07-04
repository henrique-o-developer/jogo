package antigo.World;

import java.awt.*;
import antigo.Main.Main;

public class Camera {

    public static int x = 0, y = 20;

    public static Rectangle genScreen() {
        return new Rectangle(x, y, Main.image.getWidth(), Main.image.getHeight());
    }

    public static int clamp(int v, int vMin, int vMax) {
        Main.log(v, vMin, vMax);
        if (v < vMin) {
            v = vMin;
        }

        if (v > vMax) {
            v = vMax;
        }

        Main.log(v);

        return v;
    }
}
