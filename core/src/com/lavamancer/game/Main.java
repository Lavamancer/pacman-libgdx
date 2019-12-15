package com.lavamancer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;
	public static final float VOLUME = 0.01f;

	private BitmapFont gameOverBitmapFont;
	private SpriteBatch spriteBatch;
	private List<Entity> entitiesAux = new ArrayList<>();
	public List<Entity> entities = new ArrayList<>();
	public Map map;
	public Player player;
	public boolean gameOver;


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		gameOverBitmapFont = new BitmapFont(Gdx.files.internal("minecraft.fnt"),false);
		gameOverBitmapFont.getData().setScale(3f);
		startGame();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime() * 0.5f;

		// DRAW
		spriteBatch.begin();
		map.draw(spriteBatch);
		for (Entity entity : entities) {
			entity.draw(spriteBatch);
		}

		if (gameOver) {
			gameOverBitmapFont.draw(spriteBatch, "Game Over", Gdx.graphics.getWidth() / 2f - BitmapFontTool.getWidth(gameOverBitmapFont, "Game Over") / 2, Gdx.graphics.getHeight() / 2f + 80);
		}
		spriteBatch.end();

		// UPDATE
		entitiesAux.clear();
		entitiesAux.addAll(entities);
		for (Entity entity : entitiesAux) {
			entity.update(delta);
		}
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}


	public void startGame() {
		gameOver = false;
		entities.clear();
		map = new Map(this);
		player = new Player(this);
		entities.add(player);
		entities.add(new Ghost(this, Ghost.Type.BLINKY));
		entities.add(new Ghost(this, Ghost.Type.PINKY));
		entities.add(new Ghost(this, Ghost.Type.INKY));
		entities.add(new Ghost(this, Ghost.Type.CLYDE));
	}


}
