package com.lavamancer.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.lavamancer.game.Main;

import java.util.HashMap;

public class Ghost extends Entity {

    public enum Type { BLINKY, PINKY, INKY, CLYDE }
    private Type type;
    private HashMap<Double, Direction> directionChoice = new HashMap<>();


    public Ghost(Main main, Type type) {
        super(main, "barnacle.png");
        this.type = type;
        switch (type) {
            case BLINKY: sprite.setColor(Color.RED); x = 10; y = 11; speed = 0.15f; break;
            case PINKY: sprite.setColor(Color.PINK); x = 11; y = 11; speed = 0.08f; break;
            case INKY: sprite.setColor(Color.CYAN); x = 9; y = 11; break;
            case CLYDE: sprite.setColor(Color.ORANGE); x = 10; y = 12; break;
        }
        targetX = x;
        targetY = y;
    }

    @Override
    public void update(float delta) {
        speedTick += delta;
        if (speedTick >= speed) {
            x = targetX;
            y = targetY;
            speedTick = 0;
            updateMovement();
            checkPlayerCollision();
        }
    }

    private void checkPlayerCollision() {
        if (x == main.player.x && y == main.player.y) {
            main.gameOver = true;
        }
    }

    private void updateMovement() {
        if (main.gameOver) return;

        if (existsIntersection()) {
            directionChoice.clear();

            if (!main.map.existsWall(this, Direction.UP) && direction != Direction.DOWN) {
                directionChoice.put(Math.random(), Direction.UP);
            }
            if (!main.map.existsWall(this, Direction.DOWN) && direction != Direction.UP) {
                directionChoice.put(Math.random(), Direction.DOWN);
            }
            if (!main.map.existsWall(this, Direction.LEFT) && direction != Direction.RIGHT) {
                directionChoice.put(Math.random(), Direction.LEFT);
            }
            if (!main.map.existsWall(this, Direction.RIGHT) && direction != Direction.LEFT) {
                directionChoice.put(Math.random(), Direction.RIGHT);
            }

            direction = directionChoice.get(directionChoice.keySet().stream().reduce(0d, (a, b) -> a > b ? a : b));
        } else if (isBlocked()) {
            switch (direction) {
                case UP: direction = Direction.DOWN; break;
                case DOWN: direction = Direction.UP; break;
                case LEFT: direction = Direction.RIGHT; break;
                case RIGHT: direction = Direction.LEFT; break;
            }
        }

        if (type == Type.BLINKY && existsIntersection()) {
            Direction directionPath = main.map.getPath(this, main.player);
            if (directionPath != null) {
                direction = directionPath;
            }
        }

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

    private boolean existsIntersection() {
        switch (direction) {
            case UP: case DOWN: return !main.map.existsWall(this, Direction.LEFT) || !main.map.existsWall(this, Direction.RIGHT);
            case LEFT: case RIGHT: return !main.map.existsWall(this, Direction.UP) || !main.map.existsWall(this, Direction.DOWN);
        }
        return false;
    }

    private boolean isBlocked() {
        switch (direction) {
            case UP: return main.map.existsWall(this, Direction.LEFT)
                    && main.map.existsWall(this, Direction.RIGHT)
                    && main.map.existsWall(this, Direction.UP);
            case DOWN: return main.map.existsWall(this, Direction.LEFT)
                    && main.map.existsWall(this, Direction.RIGHT)
                    && main.map.existsWall(this, Direction.DOWN);
            case LEFT: return main.map.existsWall(this, Direction.LEFT)
                    && main.map.existsWall(this, Direction.UP)
                    && main.map.existsWall(this, Direction.DOWN);
            case RIGHT: return main.map.existsWall(this, Direction.RIGHT)
                    && main.map.existsWall(this, Direction.UP)
                    && main.map.existsWall(this, Direction.DOWN);
        }
        return false;
    }

}
