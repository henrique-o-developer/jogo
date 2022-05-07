package Graphics;

import File.GifGetter;
import Main.Variables;

import java.io.File;

public class Spritesheet {
    public static GifGetter
        playerWalking = null
    ;

    public static void setVars() {
        playerWalking = new GifGetter(new File(Variables.DECOMP_DIR + "/graphics/textures/default/player/player-walking.gif"));
    }
}
