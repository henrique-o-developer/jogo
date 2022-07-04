package com.hrstd.events.start;

import java.awt.*;

public class DrawEvent extends StartEvent {
    private final Graphics g;
    private final Drawable t;

    public DrawEvent(String name, Runnable when_censelled, Graphics g, Drawable t) {
        super(name, when_censelled);

        this.g = g;
        this.t = t;
    }

    public Graphics getG() {
        return g;
    }

    public Drawable getT() {
        return t;
    }

    public static class Drawable {
        private final Rectangle pos;
        private final Object t;

        public Drawable(Rectangle pos, Object t) {
            this.pos = pos;
            this.t = t;
        }

        public Rectangle getPos() {
            return pos;
        }

        public Object getT() {
            return t;
        }
    }
}
