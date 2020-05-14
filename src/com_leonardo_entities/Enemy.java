package com_leonardo_entities;

import java.awt.image.BufferedImage;

import com_leonardo_main.Game;
import com_leonardo_main.Sound;
import com_leonardo_world.Camera;
import com_leonardo_world.World;
import java.awt.Rectangle;
// import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Entity {

    private double speed = 0.8;
    private BufferedImage[] enemySprites;
    private BufferedImage damagedEnemy;
    private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

    private int xMask = 8, yMask = 8, widthMask = 14, heightMask = 14;

    private int LIFE = 10;
    private boolean isDamaged = false;
    private int damageFrame = 0, maxDamageFrame = 5;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        enemySprites = new BufferedImage[2];
        enemySprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
        enemySprites[1] = Game.spritesheet.getSprite(112 + 16, 16, 16, 16);

        damagedEnemy = Game.spritesheet.getSprite(112, 32, 16, 16);
    }

    public void tick() {
        if (!this.isCollidingWithPlayer()) {
            if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY()) && Game.player.c == 0
                    && !isColliding((int) (x + speed), this.getY())) {
                x += speed;
            } else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
                    && Game.player.c == 0 && !isColliding((int) (x - speed), this.getY())) {
                x -= speed;
            }
            if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed)) && Game.player.c == 0
                    && !isColliding(this.getX(), (int) (y + speed))) {
                y += speed;
            } else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
                    && Game.player.c == 0 && !isColliding(this.getX(), (int) (y - speed))) {
                y -= speed;
            }
        } else {
            // Inimigo está colado no player
            // Consequencias:
            if (Game.rand.nextInt(100) < 10) {
                Sound.hurtPlayerEffect.play();
                Game.player.LIFE -= Game.rand.nextInt(3);
                Game.player.isDamaged = true;

            }

        }

        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index > maxIndex) {
                index = 0;
            }
        }

        bulletCollision();
        if (isDamaged) {
            damageFrame++;
            if (damageFrame == maxDamageFrame) {
                isDamaged = false;
                damageFrame = 0;
            }
        }

        if (LIFE <= 0)
            selfDestroy();

    }

    public void selfDestroy() {
        Game.entities.remove(this);
        Game.enemies.remove(this);
    }

    public void bulletCollision() {
        for (int i = 0; i < Game.bullets.size(); i++) {
            Entity e = Game.bullets.get(i);
            if (e instanceof Shoot) {
                if (Entity.isColliding(this, e)) {
                    Sound.hitBulletEffect.play();
                    isDamaged = true;
                    LIFE--;
                    Game.bullets.remove(i);
                    return;
                }
            }
        }

    }

    public boolean isCollidingWithPlayer() {
        Rectangle currentEnemy = new Rectangle(this.getX() + xMask, this.getY() + yMask, widthMask, heightMask);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

        return currentEnemy.intersects(player);
    }

    public boolean isColliding(int xNext, int yNext) {
        Rectangle currentEnemy = new Rectangle(xNext + xMask, yNext + yMask, widthMask, heightMask);
        for (int i = 0; i < Game.enemies.size(); i++) {
            Enemy e = Game.enemies.get(i);
            if (e == this)
                continue;
            Rectangle targetEnemy = new Rectangle(e.getX() + xMask, e.getY() + yMask, widthMask, heightMask);
            if (currentEnemy.intersects(targetEnemy)) {
                return true;
            }
        }

        return false;
    }

    public void render(Graphics g) {
        if (!isDamaged)
            g.drawImage(enemySprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        else
            g.drawImage(damagedEnemy, this.getX() - Camera.x, this.getY() - Camera.y, null);
        // g.setColor(Color.blue);
        // g.fillRect(this.getX() + xMask - Camera.x, this.getY() + yMask - Camera.y,
        // widthMask, heightMask);
    }

}