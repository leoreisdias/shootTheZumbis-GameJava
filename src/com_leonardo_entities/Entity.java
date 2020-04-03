package com_leonardo_entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
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
        g.drawImage(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }

    public void tick() {

    }
}