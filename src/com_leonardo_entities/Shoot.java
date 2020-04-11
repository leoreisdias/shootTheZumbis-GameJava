package com_leonardo_entities;

import java.awt.image.BufferedImage;

import com_leonardo_world.Camera;

import java.awt.Color;
import java.awt.Graphics;

import com_leonardo_main.Game;

public class Shoot extends Entity {

    private int dx;
    private int dy;
    private double speed = 4;

    private int life = 25, currentLife = 0;

    public Shoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
    }

    public void tick() {
        x += dx * speed;
        y += dy * speed;
        currentLife++;
        if (currentLife == life) {
            Game.bullets.remove(this);
            return;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
    }
}