package com_leonardo_world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.ImageIO;

import com_leonardo_entities.Bullet;
import com_leonardo_entities.Enemy;
import com_leonardo_entities.Entity;
import com_leonardo_entities.Gun;
import com_leonardo_entities.LifePack;
import com_leonardo_main.Game;

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
                    final int pixelAtual = pixels[xx + (yy * spriteMap.getWidth())];
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    if (pixelAtual == 0xFF000000) {
                        // Chão
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    } else if (pixelAtual == 0xFFFFFFFF) {
                        // Parede
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL);
                    } else if (pixelAtual == 0xFFFF00DC) {
                        // Player
                        Game.player.setX(xx * 16);
                        Game.player.setY(yy * 16);
                    } else if (pixelAtual == 0xFFFF0000) {
                        // Inimigo
                        Game.entities.add(new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENTITY_ENEMY));
                    } else if (pixelAtual == 0xFF0026FF) {
                        // Arma
                        Game.entities.add(new Gun(xx * 16, yy * 16, 16, 16, Entity.ENTITY_GUN));
                    } else if (pixelAtual == 0xFF00FF21) {
                        // Pack de Vida
                        Game.entities.add(new LifePack(xx * 16, yy * 16, 16, 16, Entity.ENTITY_LIFEPACK));
                    } else if (pixelAtual == 0xFFFFD800) {
                        // Munição
                        Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.ENTITY_BULLET));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        int xStart = Camera.x >> 4;
        int yStart = Camera.y >> 4;

        int xFinal = xStart + (Game.WIDTH >> 4);
        int yFinal = yStart + (Game.HEIGHT >> 4);

        for (int xx = xStart; xx <= xFinal; xx++) {
            for (int yy = yStart; yy <= yFinal; yy++) {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
                    continue;
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }
}