package com.lavamancer.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {

    public static final int TILE_SIZE = 30;
    public static final int OFFSET_X = 85;
    public static final int OFFSET_Y = 85;

    private Sprite wallSprite;
    private int[][] matrix = new int[][] {

        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0},
        {0,1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1,0},
        {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        {0,1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1,0},
        {0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0},
        {0,1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,1,0},
        {0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0},
        {1,1,1,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,1,1,1},
        {0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1},
        {0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0},
        {0,1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,0},
        {0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0},
        {0,1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1,0},
        {0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0},
        {0,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1,0},
        {0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0},
        {0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0},
        {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}

    };

    public Map() {
        Texture wallTexture = new Texture("stoneCenter.png");
        wallSprite = new Sprite(wallTexture);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int tile = getTile(i, j);
                if (tile == 1) {
                    wallSprite.setBounds(i * TILE_SIZE + OFFSET_X, j * TILE_SIZE + OFFSET_Y, TILE_SIZE, TILE_SIZE);
                    wallSprite.draw(spriteBatch);
                }
            }
        }
    }

    public int getTile(int x, int y) {
        if (x < 0 || x > getWidth() - 1 || y < 0 || y > getHeight() - 1) return 0;
        return matrix[matrix.length - y - 1][x];
    }

    public boolean existsWall(Entity entity, Entity.Direction direction) {
        switch (direction) {
            case UP: return getTile(entity.x, entity.y + 1) == 1;
            case DOWN: return getTile(entity.x, entity.y - 1) == 1;
            case LEFT: return getTile(entity.x - 1, entity.y) == 1;
            case RIGHT: return getTile(entity.x + 1, entity.y) == 1;
        }
        return false;
    }

    public int getWidth() {
        return matrix[0].length;
    }

    public int getHeight() {
        return matrix.length;
    }

}
