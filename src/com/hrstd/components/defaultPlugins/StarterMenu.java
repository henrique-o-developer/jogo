package com.hrstd.components.defaultPlugins;

import com.hrstd.components.Plugin;

import java.awt.*;

public class StarterMenu implements Plugin {
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public String getContextToEnable() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    public static class Menu {
        protected final MenuParam[] params;
        //protected final
        private int selected = -1;
        private boolean activated = true;

        public Menu(MenuParam[] params) {
            this.params = params;
        }

        public void tick() {
            if (!activated) return;

            if (selected == -1) return;

            if (params[selected] == null) return;

            params[selected].run();

            activated = false;
        }

        public void render(Graphics g) {
            if (!activated) return;


        }

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
}
