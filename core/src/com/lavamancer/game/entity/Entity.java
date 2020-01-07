package com.lavamancer.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavamancer.game.tool.AssetTool;
import com.lavamancer.game.Main;
import com.lavamancer.game.Map;

public abstract class Entity {

    public enum Direction { UP, DOWN, RIGHT, LEFT }

    public Sprite sprite;
    public Main main;
    public float speed = 0.1f;
    public Direction direction = Direction.RIGHT;
    public float speedTick;
    public int x;
    public int y;
    public int targetX;
    public int targetY;


    public Entity(Main main, String spritePath) {
        this.main = main;
        sprite = new Sprite((Texture) AssetTool.getInstance().load(spritePath, Texture.class));
    }

    public void draw(SpriteBatch spriteBatch) {
        float offsetX = 0;
        float offsetY = 0;
        float offset = (speedTick / speed) * Map.TILE_SIZE;
        if (targetX == x && targetY == y) {
            offset = 0;
        }
        switch (direction) {
            case UP: offsetY = offset; break;
            case DOWN: offsetY = -offset; break;
            case LEFT: offsetX = -offset; break;
            case RIGHT: offsetX = offset; break;
        }


        sprite.setBounds(x * Map.TILE_SIZE + Map.OFFSET_X + offsetX, y * Map.TILE_SIZE + Map.OFFSET_Y + 4 + offsetY, Map.TILE_SIZE , Map.TILE_SIZE);
        sprite.draw(spriteBatch);
    }

    public abstract void update(float delta);

    public void checkMapBoundaries() {
        if (targetX > main.map.getWidth() - 1) targetX = 0;
        if (targetX < 0) targetX = main.map.getWidth() - 1;
        if (targetY > main.map.getHeight() - 1) targetY = 0;
        if (targetY < 0) targetY = main.map.getHeight() - 1;
    }
}
