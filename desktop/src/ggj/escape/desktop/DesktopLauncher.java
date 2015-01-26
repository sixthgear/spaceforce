package ggj.escape.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ggj.escape.EscapeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = Gdx.graphics.getWidth();
//		config.height = Gdx.graphics.getHeight();
//		config.fullscreen = true;
		new LwjglApplication(new EscapeGame(), config);
	}
}
