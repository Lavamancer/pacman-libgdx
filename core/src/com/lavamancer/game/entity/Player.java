package com.lavamancer.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lavamancer.game.Main;

public class Player extends Entity {

    public int score;


    public Player(Main main) {
        super(main, "slimeBlock.png");
        x = 10;
        y = 5;
        direction = Direction.DOWN;
    }

    @Override
    public void update(float delta) {
        updateInput();

        speedTick += delta;
        if (speedTick >= speed) {
            speedTick = 0;
            updateMovement();
        }
    }

    private void updateInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            main.startGame();
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
        if (!existsWall() && !main.gameOver) {
            switch (direction) {
                case UP: y++; break;
                case DOWN: y--; break;
                case RIGHT: x++; break;
                case LEFT: x--; break;
            }
            checkMapBoundaries();
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
