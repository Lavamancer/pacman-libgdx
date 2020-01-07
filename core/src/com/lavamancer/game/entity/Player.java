package com.lavamancer.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lavamancer.game.Main;

public class Player extends Entity {

    public int score;
    private Direction nextDirection;


    public Player(Main main) {
        super(main, "slimeBlock.png");
        x = 10;
        y = 5;
        targetX = x;
        targetY = y;
        direction = Direction.LEFT;
        nextDirection = direction;
    }

    @Override
    public void update(float delta) {
        updateInput();

        speedTick += delta;
        if (speedTick >= speed || (targetX == x && targetY == y)) {
            x = targetX;
            y = targetY;
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
            nextDirection = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nextDirection = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nextDirection = Direction.RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nextDirection = Direction.LEFT;
        }
    }

    private void updateMovement() {
        if (!existsWall() && !main.gameOver) {
            direction = nextDirection;
            targetX = x;
            targetY = y;
            switch (direction) {
                case UP: targetY++; break;
                case DOWN: targetY--; break;
                case RIGHT: targetX++; break;
                case LEFT: targetX--; break;
            }
            checkMapBoundaries();
        }
    }

    private boolean existsWall() {
        int tile = 0;
        switch (nextDirection) {
            case UP: tile = main.map.getTile(targetX, targetY + 1); break;
            case DOWN: tile = main.map.getTile(targetX, targetY - 1); break;
            case RIGHT: tile = main.map.getTile(targetX + 1, targetY); break;
            case LEFT: tile = main.map.getTile(targetX - 1, targetY); break;
        }
        return tile == 1;
    }

}
