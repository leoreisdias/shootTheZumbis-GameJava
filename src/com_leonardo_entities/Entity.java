package com_leonardo_entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com_leonardo_main.Game;
import com_leonardo_world.Camera;

public class Entity {

    public static BufferedImage ENTITY_LIFEPACK = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
    public static BufferedImage ENTITY_GUN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
    public static BufferedImage ENTITY_BULLET = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
    public static BufferedImage ENTITY_ENEMY = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;

    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void setX(int X) {
        this.x = X;
    }

    public void setY(int Y) {
        this.y = Y;
    }

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight(), null);
    }

    public void tick() {

    }
}