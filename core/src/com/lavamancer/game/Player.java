package com.lavamancer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    private enum Direction { UP, DOWN, RIGHT, LEFT }
    private static final float SPEED = 0.1f;

    private Main main;
    private Sprite sprite;
    private int x = 10;
    private int y = 5;
    private Direction direction = Direction.RIGHT;
    private float speedTick;


    public Player(Main main) {
        this.main = main;
        this.sprite = new Sprite(new Texture("slimeBlock.png"));
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.setBounds(x * Map.TILE_SIZE + Map.OFFSET_X, y * Map.TILE_SIZE + Map.OFFSET_Y + 4, Map.TILE_SIZE , Map.TILE_SIZE);
        sprite.draw(spriteBatch);
    }

    public void update(float delta) {
        updateInput();

        speedTick += delta;
        if (speedTick >= SPEED) {
            speedTick = 0;
            updateMovement();
        }
    }

    private void updateInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction = Direction.RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction = Direction.LEFT;
        }
    }

    private void updateMovement() {
        if (!existsWall()) {
            switch (direction) {
                case UP: y++; break;
                case DOWN: y--; break;
                case RIGHT: x++; break;
                case LEFT: x--; break;
            }
        }
    }

    private boolean existsWall() {
        int tile = 0;
        switch (direction) {
            case UP: tile = main.map.getTile(x, y + 1); break;
            case DOWN: tile = main.map.getTile(x, y - 1); break;
            case RIGHT: tile = main.map.getTile(x + 1, y); break;
            case LEFT: tile = main.map.getTile(x - 1, y); break;
        }
        return tile == 1;
    }

}
