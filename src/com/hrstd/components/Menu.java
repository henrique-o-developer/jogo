package com.hrstd.components;

import java.awt.*;

public class Menu {
    private MenuParam[] params;

    public Menu(MenuParam[] params) {
        this.params = params;
    }

    public void render(Graphics g) {

    }

    public void tick() {}

    public static class MenuParam {
        private final Runnable r;
        private final String name;

        public MenuParam(String name, Runnable r) {
            this.name = name;
            this.r = r;
        }

        public String getName() {
            return name;
        }

        public void run() {
            r.run();
        }
    }
}
