package com_leonardo_main;

import java.applet.AudioClip;
import java.applet.Applet;

public class Sound {
    private AudioClip clip;

    public static final Sound musicBackground = new Sound("/music.wav");
    public static final Sound hurtPlayerEffect = new Sound("/HurtPlayer.wav");
    public static final Sound hitBulletEffect = new Sound("/HitBullet.wav");

    public Sound(String name) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(name));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void play() {
        try {
            new Thread() {
                public void run() {
                    clip.play();
                }
            }.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void loop() {
        try {
            new Thread() {
                public void run() {
                    clip.loop();
                }
            }.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}