package com.hrstd.events.start;

public class KeybordInputEvent extends StartEvent {
    private final String type;
    private final char key;
    private final int code;

    public KeybordInputEvent(String name, Runnable when_censelled, int code, char key, String type) {
        super(name, when_censelled);

        this.code = code;
        this.key = key;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public char getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "KeybordInputEvent {" +
                "type='" + type + '\'' +
                ", key=" + key +
                ", code=" + code +
                '}';
    }
}
