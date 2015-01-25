package ggj.escape;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class EscapeGame extends Game {

	private double DT = 1.0/45.0f;
	private double accumulator = 0.0;
	private double t = 0.0;

	public GameScreen game;

	@Override
	public void create () {
		game = new GameScreen();
		setScreen(game);
	}

	@Override
	public void render () {

		// fixed timestep consumes time from accumulator in DT sized chunks
		accumulator += Gdx.graphics.getDeltaTime();
		while (accumulator >= DT) {
			game.update((float) DT);
			accumulator -= DT;
			t += DT;
		}

		double alpha = accumulator / DT;

		// clear window
		Gdx.gl.glClearColor(0.03f, 0.03f, 0.03f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render current screen
		game.render((float) t, (float) DT, (float) alpha);

	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}

}
