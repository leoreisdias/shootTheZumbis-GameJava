package com_leonardo_world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.ImageIO;

public class World {

    private Tile[] tiles;
    public static int WIDTH, HEIGHT;

    public World(String path) {
        try {
            BufferedImage spriteMap = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[spriteMap.getWidth() * spriteMap.getHeight()];
            WIDTH = spriteMap.getWidth();
            HEIGHT = spriteMap.getHeight();
            tiles = new Tile[spriteMap.getWidth() * spriteMap.getHeight()];
            spriteMap.getRGB(0, 0, spriteMap.getWidth(), spriteMap.getHeight(), pixels, 0, spriteMap.getWidth());
            for (int xx = 0; xx < spriteMap.getWidth(); xx++) {
                for (int yy = 0; yy < spriteMap.getHeight(); yy++) {
                    int pixelAtual = pixels[xx + (yy * spriteMap.getWidth())];
                    if (pixelAtual == 0xFF000000) {
                        // Chão
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    } else if (pixelAtual == 0xFFFFFFFF) {
                        // Colisão com Parede
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL);

                    } else if (pixelAtual == 0xFFFF00E1) {
                        // Player
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);

                    } else {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        for (int xx = 0; xx < WIDTH; xx++) {
            for (int yy = 0; yy < HEIGHT; yy++) {
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }
}