package com_leonardo_graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com_leonardo_entities.Player;

public class UI {

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(8, 4, 50, 8);
        g.setColor(Color.green);
        g.fillRect(8, 4, (50 * Player.LIFE) / Player.maxLIFE, 8);

        g.setColor(Color.black);
        g.setFont(new Font("arial", Font.BOLD, 8));
        g.drawString(Player.LIFE + " / " + Player.maxLIFE, 17, 11);
    }

}