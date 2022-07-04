package com.hrstd.components;

import java.awt.Graphics;

public interface Plugin {
    void onEnable();
    void onDisable();
    void tick();
    void render(Graphics g);
    String getContextToEnable();
    String getName();
    String[] getDependencies();
}
