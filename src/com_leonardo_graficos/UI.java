package com_leonardo_graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com_leonardo_main.Game;

public class UI {

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(8, 4, 50, 8);
        g.setColor(Color.green);
        g.fillRect(8, 4, (50 * Game.player.LIFE) / Game.player.maxLIFE, 8);

        g.setColor(Color.black);
        g.setFont(new Font("arial", Font.BOLD, 8));
        g.drawString(Game.player.LIFE + " / " + Game.player.maxLIFE, 17, 11);
    }

}