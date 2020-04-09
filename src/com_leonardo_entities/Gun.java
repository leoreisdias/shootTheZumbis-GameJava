package com_leonardo_entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import com_leonardo_main.Game;
import com_leonardo_world.Camera;

public class Gun extends Entity {

    private BufferedImage[] gunSprite;
    private int frames = 0, maxFrames = 15, index = 0, maxIndex = 1;

    public Gun(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        gunSprite = new BufferedImage[2];
        gunSprite[0] = Game.spritesheet.getSprite(112, 0, 16, 16);
        gunSprite[1] = Game.spritesheet.getSprite(112 + 16, 0, 16, 16);
    }

    public void tick() {
        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index > maxIndex) {
                index = 0;
            }
        }

    }

    public void render(Graphics g) {
        g.drawImage(gunSprite[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

}