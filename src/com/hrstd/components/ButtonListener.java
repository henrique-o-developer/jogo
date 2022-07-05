package com.hrstd.components;

import com.hrstd.events.EventListener;
import com.hrstd.events.start.MouseClickEvent;
import com.hrstd.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ButtonListener {

    public static class Button {}

    public static class MouseClicable extends Button {
        BufferedImage texture;
        Interface r;
        Rectangle pos;
        boolean enabled = true;

        public MouseClicable(BufferedImage texture, Interface r, Rectangle p) {
            this.r = r;
            this.texture = texture;
            this.pos = p;

            EventListener.def.addListener((e, remove) -> {
                if (!enabled) {
                    remove.run();
                    return;
                }

                if (!(e instanceof MouseClickEvent)) return;
                if (!Objects.equals(((MouseClickEvent) e).getType(), "clicked")) return;

                int x = ((MouseClickEvent) e).getX() / (frame.getWidth() / DWIDTH);
                int y = ((MouseClickEvent) e).getY() / (frame.getHeight() / DHEIGHT);

                Rectangle i = new Rectangle(x, y, 1, 1);

                if (i.intersects(p)) {
                    r.run(this::disable);
                }
            });
        }

        public void render(Graphics g) {
            g.drawImage(texture, pos.x, pos.y, pos.width, pos.height, null);
        }

        public void disable() {
            enabled = false;
        }
    }

    public static class KeybordInteractive extends Button {}

    public static class All {}

    public static interface Interface {
        void run(Runnable disable);
    }
}
