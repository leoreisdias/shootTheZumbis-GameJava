package com_leonardo_entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com_leonardo_main.Game;
import com_leonardo_world.Camera;
import com_leonardo_world.World;

public class Player extends Entity {

    public boolean right, up, left, down;
    public int direction = 0;
    public int right_direction = 0, left_direction = 1;
    public double speed = 1;

    private int frames = 0, maxFrames = 4, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;

    private BufferedImage playerDamaged;

    public boolean isDamaged = false;
    private int damageFrame = 0;
    public int AMMO = 0;
    public int LIFE = 100, maxLIFE = 100;
    public boolean hasGun = false;

    public boolean isShooting = false, mouseShoot = false;

    public int mouseX, mouseY;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        playerDamaged = Game.spritesheet.getSprite(0, 16, width, height);

        for (int i = 0; i < 4; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
            leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    public void tick() {
        moved = false;
        if (right && World.isFree((int) (x + speed), this.getY())) {
            moved = true;
            direction = right_direction;
            x += speed;
        } else if (left && World.isFree((int) (x - speed), this.getY())) {
            moved = true;
            direction = left_direction;
            x -= speed;

        }
        if (up && World.isFree(this.getX(), (int) (y - speed))) {
            moved = true;
            y -= speed;
        } else if (down && World.isFree(this.getX(), (int) (y + speed))) {
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

        checkLifepackApproach();
        checkAmmoApproach();
        checkGunApproach();

        if (isDamaged) {
            this.damageFrame++;
            if (this.damageFrame == 5) {
                this.damageFrame = 0;
                isDamaged = false;
            }
        }

        if (isShooting) {
            isShooting = false;
            if (hasGun && AMMO > 0) {
                AMMO--;
                int dx = 0;
                int posX = 0;
                int posY = 0;
                if (direction == right_direction) {
                    posX = this.getX() + 20;
                    posY = this.getY() + 8;
                    dx = 1;
                } else {
                    posX = this.getX() - 10;
                    posY = this.getY() + 8;
                    dx = -1;
                }

                Shoot bullet = new Shoot(posX, posY, 3, 3, null, dx, 0);
                Game.bullets.add(bullet);
            }
        }

        if (mouseShoot) {

            mouseShoot = false;
            if (hasGun && AMMO > 0) {
                AMMO--;
                double angle = Math.atan2(mouseY - (this.getY() + 8 - Camera.y), mouseX - (this.getX() + 8 - Camera.x));
                double dx = Math.cos(angle);
                double dy = Math.sin(angle);
                int posX = 8;
                int posY = 8;
                if (direction == right_direction) {
                    posX = this.getX() + 20;
                    posY = this.getY() + 8;
                    dx = 1;
                } else {
                    posX = this.getX() - 10;
                    posY = this.getY() + 8;
                    dx = -1;
                }

                Shoot bullet = new Shoot(posX, posY, 3, 3, null, dx, dy);
                Game.bullets.add(bullet);
            }
        }

        if (LIFE <= 0) {
            // World.deathWorld();
            Game.gameState = "GAMEOVER";
        }

        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);

    }

    public void checkGunApproach() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Gun) {
                if (Entity.isColliding(this, current)) {
                    hasGun = true;
                    // System.out.println("ARMA NELE");
                    Game.entities.remove(current);
                }
            }
        }
    }

    public void checkAmmoApproach() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Bullet) {
                if (Entity.isColliding(this, current)) {
                    AMMO += 10;
                    System.out.println(AMMO);
                    Game.entities.remove(current);
                }
            }
        }
    }

    public void checkLifepackApproach() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof LifePack) {
                if (Entity.isColliding(this, current)) {
                    LIFE += 8;
                    if (LIFE >= 100) {
                        LIFE = 100;
                    }
                    Game.entities.remove(current);
                    return;
                }
            }
        }
    }

    public void render(Graphics g) {
        if (!isDamaged) {
            if (direction == right_direction) {
                g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                if (hasGun) {
                    // Desenhar arma na direita
                    g.drawImage(Entity.ENTITY_GUN_RIGHT, this.getX() - Camera.x + 10, this.getY() - Camera.y, null);
                }
            } else if (direction == left_direction) {
                g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                if (hasGun) {
                    // Desenhar arma na esquerda
                    g.drawImage(Entity.ENTITY_GUN_LEFT, this.getX() - Camera.x - 10, this.getY() - Camera.y, null);

                }
            }
        } else {
            g.drawImage(playerDamaged, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }
}