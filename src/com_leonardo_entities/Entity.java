package com_leonardo_entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com_leonardo_main.Game;
import com_leonardo_world.Camera;
import java.awt.Rectangle;
import java.awt.Color;

public class Entity {

    public static BufferedImage ENTITY_LIFEPACK = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
    public static BufferedImage ENTITY_GUN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
    public static BufferedImage ENTITY_BULLET = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
    public static BufferedImage ENTITY_ENEMY = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;

    private int xMask, yMask, widthMask, heightMask;

    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        this.xMask = 0;
        this.yMask = 0;
        this.widthMask = width;
        this.heightMask = height;
    }

    public void setMask(int xMask, int yMask, int widthMask, int heightMask) {
        this.xMask = xMask;
        this.yMask = yMask;
        this.widthMask = widthMask;
        this.heightMask = heightMask;
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

    public void tick() {

    }

    public static boolean isColliding(Entity e1, Entity e2) {
        Rectangle e1Rect = new Rectangle(e1.getX() + e1.xMask, e1.getY() + e1.yMask, e1.widthMask, e1.heightMask);
        Rectangle e2Rect = new Rectangle(e2.getX() + e2.xMask, e2.getY() + e2.yMask, e2.widthMask, e2.heightMask);

        return e1Rect.intersects(e2Rect);
    }

    public void render(Graphics g) {
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight(), null);
        // g.setColor(Color.red);
        // g.fillRect(this.getX() + xMask - Camera.x, this.getY() + yMask - Camera.y,
        // widthMask, heightMask);
    }

}