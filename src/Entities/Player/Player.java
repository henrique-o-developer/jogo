package Entities.Player;

import Entities.*;
import Graphics.Gif;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    public List<String> moves = new ArrayList<>();
    private byte mvX, mvY;
    private boolean canJump = true;
    public Player(int x, int y, int w, int h, Gif gif) {
        super(x, y, w, h, gif);
    }

    public void move(String dir, boolean stop) {
        moves.add(dir);

        switch (dir) {
            case "u": {
                mvY = -1;

                if (stop) mvY = 0;
                break;
            }

            case "l": {
                mvX = -1;

                if (stop) mvX = 0;
                break;
            }

            case "r": {
                mvX = 1;

                if (stop) mvX = 0;
                break;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        switch (mvX) {
            case -1: {
                x -= 3;

                if (gif != originalGifFlipped) {
                    gif = originalGifFlipped;

                    gif.resetAnimation();
                }

                break;
            }

            case 1: {
                x += 3;

                if (gif != originalGif) {
                    gif = originalGif;

                    gif.resetAnimation();
                }

                break;
            }

            //case 0 -> {}
        }

        switch (mvY) {
            case -1: {
                if (!canJump) break;

                vy -= 1;

                if (Math.abs(vy) > 3) canJump = false;

                break;
            }
        }

        while (moves.size() > 500) moves.remove(500);
    }

    public byte getMvX() {
        return mvX;
    }

    public byte getMvY() {
        return mvY;
    }
}
