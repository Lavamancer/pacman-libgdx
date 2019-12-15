package com.lavamancer.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Dot extends Entity {

    boolean isGiant;
    Sound sound;


    public Dot(Main main, int x, int y, boolean isGiant) {
        super(main, "dot.png");
        this.isGiant = isGiant;
        this.x = x;
        this.y = y;
        if (isGiant) {
            sprite = new Sprite(new Texture("hudJewel_blue.png"));
        }
        sound = AssetTool.getInstance().load("pacman_chomp.ogg", Sound.class);

    }


    @Override
    public void update(float delta) {
        if (x == main.player.x && y == main.player.y) {
            main.entities.remove(this);
            long soundId = sound.play();
            sound.setVolume(soundId, Main.VOLUME);
            sound.setPitch(soundId, 1.5f);
        }
    }
}
