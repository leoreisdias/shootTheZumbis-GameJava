package com_leonardo_entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com_leonardo_main.Game;

public class Player extends Entity {

    public boolean right, up, left, down;
    public int direction = 0;
    public int right_direction = 0, left_direction = 1;
    public double speed = 1;

    private int frames = 0, maxFrames = 4, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
            leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    public void tick() {
        moved = false;
        if (right) {
            moved = true;
            direction = right_direction;
            x += speed;
        } else if (left) {
            moved = true;
            direction = left_direction;
            x -= speed;

        }
        if (up) {
            moved = true;
            y -= speed;
        } else if (down) {
            moved = true;
            y += speed;
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                index++;
                if (index > maxIndex) {
                    index = 0;
                }
            }
        } else {
            frames = 0;
            index = 0;
        }
    }

    public void render(Graphics g) {
        if (direction == right_direction) {
            g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
        } else if (direction == left_direction) {
            g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
        }
    }
}