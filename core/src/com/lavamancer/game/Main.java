package com.lavamancer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavamancer.game.entity.Entity;
import com.lavamancer.game.entity.Ghost;
import com.lavamancer.game.entity.Player;
import com.lavamancer.game.tool.AssetTool;
import com.lavamancer.game.tool.BitmapFontTool;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;
	public static final float VOLUME = 0.01f;

	private BitmapFont bitmapFont;
	private SpriteBatch spriteBatch;
	private List<Entity> entitiesAux = new ArrayList<>();
	public List<Entity> entities = new ArrayList<>();
	public Map map;
	public Player player;
	public boolean gameOver;
	private Sprite backgroundSprite;


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		bitmapFont = AssetTool.getInstance().load("minecraft.fnt", BitmapFont.class);
		backgroundSprite = new Sprite(new Texture("background.jpeg"));
		backgroundSprite.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		startGame();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime() * 0.5f;

		// DRAW
		spriteBatch.begin();
//		backgroundSprite.draw(spriteBatch);

		map.draw(spriteBatch);
		for (Entity entity : entities) {
			entity.draw(spriteBatch);
		}

		bitmapFont.setColor(Color.WHITE);
		if (gameOver) {
			bitmapFont.getData().setScale(3f);
			bitmapFont.draw(spriteBatch, "Game Over", Gdx.graphics.getWidth() / 2f - BitmapFontTool.getWidth(bitmapFont, "Game Over") / 2, Gdx.graphics.getHeight() / 2f + 80);
		}
		bitmapFont.getData().setScale(0.7f);
		bitmapFont.draw(spriteBatch, "Score: " + player.score, 10, Gdx.graphics.getHeight() - 10);

		bitmapFont.draw(spriteBatch, "Keys: W A S D R", 10, 50);

		bitmapFont.getData().setScale(1.5f);
		bitmapFont.setColor(Color.YELLOW);
		bitmapFont.draw(spriteBatch, "Pacmancer", Gdx.graphics.getWidth() / 2f - BitmapFontTool.getWidth(bitmapFont, "Pacmancer") / 2, Gdx.graphics.getHeight() - 10);
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
