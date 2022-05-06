package Graphics;

import Main.Variables;

public class Spritesheet {
    public static Gif
        playerWalking = null
    ;

    public static void setVars() {
        playerWalking = new Gif(Variables.DECOMP_DIR+"/graphics/textures/default/player/player-walking.gif");
    }
}
