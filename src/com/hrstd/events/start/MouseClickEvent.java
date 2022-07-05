package com.hrstd.events.start;

public class MouseClickEvent extends StartEvent {
    private final int x, y;
    private final String type;

    public MouseClickEvent(String name, Runnable when_censelled, int x, int y, String type) {
        super(name, when_censelled);

        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "MouseClickEvent {" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                '}';
    }
}
