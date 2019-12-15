package com.lavamancer.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {

    public enum Direction { UP, DOWN, RIGHT, LEFT }

    public Sprite sprite;
    public Main main;
    public float speed = 0.1f;
    public Direction direction = Direction.RIGHT;
    public float speedTick;
    public int x;
    public int y;


    public Entity(Main main, String spritePath) {
        this.main = main;
        sprite = new Sprite((Texture) AssetTool.getInstance().load(spritePath, Texture.class));
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.setBounds(x * Map.TILE_SIZE + Map.OFFSET_X, y * Map.TILE_SIZE + Map.OFFSET_Y + 4, Map.TILE_SIZE , Map.TILE_SIZE);
        sprite.draw(spriteBatch);
    }

    public abstract void update(float delta);


    public void checkMapBoundaries() {
        if (x > main.map.getWidth() - 1) x = 0;
        if (x < 0) x = main.map.getWidth() - 1;
        if (y > main.map.getHeight() - 1) y = 0;
        if (y < 0) y = main.map.getHeight() - 1;
    }
}
