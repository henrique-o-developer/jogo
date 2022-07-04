package antigo.World;

import java.awt.*;

public class MapManipulator {
    GenerateMap.GeneratedMap map;

    public MapManipulator(GenerateMap.GeneratedMap map) {
        this.map = map;
    }

    public void render(Graphics g) {
        //System.out.println(Camera.genScreen());
        //System.out.println(Arrays.toString(map.getInsideCamera("all")));
        for (Tile all : map.getInsideCamera("all")) {
            all.render(g);
        }
    }
}
