package com.lavamancer.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lavamancer.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Main.SCREEN_WIDTH;
		config.height = Main.SCREEN_HEIGHT;
		new LwjglApplication(new Main(), config);
	}
}
