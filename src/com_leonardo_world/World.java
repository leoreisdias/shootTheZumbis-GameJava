package com_leonardo_world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {

    public World(String path) {
        try {
            BufferedImage spriteMap = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[spriteMap.getWidth() * spriteMap.getHeight()];
            spriteMap.getRGB(0, 0, spriteMap.getWidth(), spriteMap.getHeight(), pixels, 0, spriteMap.getWidth());
            for (int i = 0; i < pixels.length; i++) {
                if (pixels[i] == 0xFFFFD800) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}