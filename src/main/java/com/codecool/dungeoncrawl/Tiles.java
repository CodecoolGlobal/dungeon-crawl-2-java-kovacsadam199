package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("closed_door", new Tile(3, 3));
        tileMap.put("open_door", new Tile(4, 3));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("key", new Tile(18, 23));
        tileMap.put("sword", new Tile(0, 30));
        tileMap.put("scorpion", new Tile(24, 5));
        tileMap.put("bee", new Tile(26, 5));
        tileMap.put("warrior", new Tile(27, 2));
        tileMap.put("fence", new Tile(3, 5));
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("simpleTree", new Tile(4, 2));
        tileMap.put("axe", new Tile(10, 29));
        tileMap.put("cutTree", new Tile(5, 0));
        tileMap.put("crown", new Tile(11, 24));
        tileMap.put("stairs", new Tile(2, 6));


    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
