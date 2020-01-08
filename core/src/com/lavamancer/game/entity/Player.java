package com.lavamancer.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavamancer.game.Main;
import com.lavamancer.game.Map;
import com.lavamancer.game.tool.AssetTool;

public class Player extends Entity {

    public int score;
    private Direction nextDirection;
    Sprite[] sprites = new Sprite[7];
    Animation<Sprite> animation;
    float totalDelta;


    public Player(Main main) {
        super(main, "slimeBlock.png");
        x = 10;
        y = 5;
        targetX = x;
        targetY = y;
        direction = Direction.LEFT;
        nextDirection = direction;
        sprites[0] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock.png", Texture.class));
        sprites[1] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock_1.png", Texture.class));
        sprites[2] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock_2.png", Texture.class));
        sprites[3] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock_3.png", Texture.class));
        sprites[4] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock_4.png", Texture.class));
        sprites[5] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock_5.png", Texture.class));
        sprites[6] = new Sprite((Texture) AssetTool.getInstance().load("slimeBlock_6.png", Texture.class));
        animation = new Animation<>(0.033f, sprites);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
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

        totalDelta += Gdx.graphics.getDeltaTime();
        sprite = animation.getKeyFrame(totalDelta, true);

        sprite.setBounds(x * Map.TILE_SIZE + Map.OFFSET_X + offsetX, y * Map.TILE_SIZE + Map.OFFSET_Y + 4 + offsetY, Map.TILE_SIZE , Map.TILE_SIZE);
        sprite.draw(spriteBatch);
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
