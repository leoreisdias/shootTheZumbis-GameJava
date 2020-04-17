package com_leonardo_main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {

    public String[] options = { "Novo Jogo", "Carregar Jogo Salvo", "Sair" };

    public int currentOption = 0;
    public int maxOptions = options.length - 1;

    public boolean up = false;
    public boolean down = false;

    public void tick() {
        if (up) {
            up = false;
            currentOption--;
            if (currentOption < 0) {
                currentOption = maxOptions;
            }
        }
        if (down) {
            down = false;
            currentOption++;
            if (currentOption > maxOptions) {
                currentOption = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        g.setColor(new Color(100, 0, 150));
        g.setFont(new Font("arial", Font.BOLD, 30));
        g.drawString("# Shoot The Zumbies #", (Game.WIDTH * Game.SCALE) / 2 - 160,
                (Game.HEIGHT * Game.SCALE) / 2 - 120);

        // Opções de MENU
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 24));
        g.drawString("Novo Jogo", (Game.WIDTH * Game.SCALE) / 2 - 65, (Game.HEIGHT * Game.SCALE) / 2 - 30);

        g.setFont(new Font("arial", Font.BOLD, 24));
        g.drawString("Carregar Jogo Salvo", (Game.WIDTH * Game.SCALE) / 2 - 120, (Game.HEIGHT * Game.SCALE) / 2 + 20);

        g.setFont(new Font("arial", Font.BOLD, 24));
        g.drawString("Sair", (Game.WIDTH * Game.SCALE) / 2 - 30, (Game.HEIGHT * Game.SCALE) / 2 + 70);

        if (options[currentOption] == "Novo Jogo") {
            g.drawString("->", (Game.WIDTH * Game.SCALE) / 2 - 100, (Game.HEIGHT * Game.SCALE) / 2 - 30);

        } else if (options[currentOption] == "Carregar Jogo Salvo") {
            g.drawString("->", (Game.WIDTH * Game.SCALE) / 2 - 170, (Game.HEIGHT * Game.SCALE) / 2 + 20);

        } else if (options[currentOption] == "Sair") {
            g.drawString("->", (Game.WIDTH * Game.SCALE) / 2 - 80, (Game.HEIGHT * Game.SCALE) / 2 + 70);

        }
    }
}