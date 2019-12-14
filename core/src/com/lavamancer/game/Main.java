package com.lavamancer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;

	SpriteBatch spriteBatch;
	Map map;
	List<Entity> entities = new ArrayList<>();


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		map = new Map();
		entities.add(new Player(this));
		entities.add(new Ghost(this, Ghost.Type.BLINKY));
		entities.add(new Ghost(this, Ghost.Type.PINKY));
		entities.add(new Ghost(this, Ghost.Type.INKY));
		entities.add(new Ghost(this, Ghost.Type.CLYDE));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime();

		// DRAW
		spriteBatch.begin();
		map.draw(spriteBatch);
		for (Entity entity : entities) {
			entity.draw(spriteBatch);
		}
		spriteBatch.end();

		// UPDATE
		for (Entity entity : entities) {
			entity.update(delta);
		}
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
