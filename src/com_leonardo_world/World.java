package com_leonardo_world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.imageio.ImageIO;

import com_leonardo_entities.Bullet;
import com_leonardo_entities.Enemy;
import com_leonardo_entities.Entity;
import com_leonardo_entities.Gun;
import com_leonardo_entities.LifePack;
import com_leonardo_entities.Player;
import com_leonardo_graficos.Spritesheet;
import com_leonardo_main.Game;

public class World {

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = 16;
    public static boolean inDeathWorld = false;

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
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    if (pixelAtual == 0xFF000000) {
                        // Chão
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    } else if (pixelAtual == 0xFFFFFFFF) {
                        // Parede
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
                    } else if (pixelAtual == 0xFFFF00DC) {
                        // Player
                        Game.player.setX(xx * 16);
                        Game.player.setY(yy * 16);
                    } else if (pixelAtual == 0xFFFF0000) {
                        // Inimigo
                        Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENTITY_ENEMY);
                        Game.entities.add(en);
                        Game.enemies.add(en);
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

    public static boolean isFree(int xNext, int yNext) {
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        if (!((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
                || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
                || (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
                || (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))) {
            return true;
        }
        if (Game.player.Z > 0)
            return true;

        return false;
    }

    // -> CASO QUEIRA UM MUNDO PÓS MORTE
    // public static void deathWorld() {
    // inDeathWorld = true;
    // Game.entities = new ArrayList<Entity>();
    // Game.enemies = new ArrayList<Enemy>();
    // Game.spritesheet = new Spritesheet("/spritesheet.png");
    // Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16,
    // 16));
    // Game.entities.add(Game.player);
    // Game.world = new World("/death.png");
    // return;
    // }

    public static void levelingGame(String level) {
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        Game.spritesheet = new Spritesheet("/spritesheet.png");
        Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
        Game.entities.add(Game.player);
        Game.world = new World("/" + level);
        return;
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