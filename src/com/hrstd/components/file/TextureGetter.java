package com.hrstd.components.file;

import com.hrstd.components.KeyMap;

import java.io.File;

public class TextureGetter {
    KeyMap t = new KeyMap();

    public void setAll(String path) {
        String[] get = {
                "player.player-walking",
                "player.player-standard",
                "tiles.grass",
                "tiles.sky",
                "tiles.dirt",
                "misc.button",
                "misc.background"
        };

        for (String s : get) {
            t.add(s, new GifGetter(new File(path + "/" + s.replace(".", "/") + ".gif")).getGif());
        }
    }

    public KeyMap.Value get(String v) {
        return t.get(v);
     }

    public String toString() {
        return t.toString();
    }
}
