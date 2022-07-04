package antigo.File;

import antigo.GifGetter;
import antigo.Main.KeyMap;
import java.io.File;

public class TextureGetter {
    KeyMap t = new KeyMap();

    public void setAll(String path) {
        String[] get = {
                "player.player-walking",
                "player.player-standard",
                "tiles.grass",
                "tiles.sky",
                "tiles.dirt"
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
