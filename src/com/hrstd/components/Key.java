package com.hrstd.components;

import java.awt.event.KeyEvent;
import java.util.Objects;

public class Key {
    private final String key;
    private final int code;

    public Key(String key, int code) {
        this.key = key;
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public int getCode() {
        return code;
    }

    public static Key getByCode(int code) {
        return new Key(KeyEvent.getKeyText(code), code);
    }

    public static Key getByKey(String key) {
        int code = 0;

        for (int i = 0; i < 1000000; i++) {
            String text = java.awt.event.KeyEvent.getKeyText(i);
            if (text.equals(key.toLowerCase())) {
                code = i;

                break;
            }
        }

        return new Key(key, code);
    }

    public boolean equals(int code) {
        return this.code == code;
    }

    public boolean equals(String key) {
        return Objects.equals(this.key, key);
    }

    public boolean equals(Key k) {
        return code == k.code || key.equals(k.key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key1 = (Key) o;
        return getCode() == key1.getCode() && getKey().equals(key1.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getCode());
    }
}
