package ggj.escape;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class EscapeGame extends Game {

	private double DT = 1.0/45.0f;
	private double accumulator = 0.0;
	private double t = 0.0;

	public GameScreen gameScreen;
	public TitleScreen titleScreen;
	public CreditsScreen creditsScreen;

	@Override
	public void create () {

		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);

		titleScreen = new TitleScreen(this);
		creditsScreen= new CreditsScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(titleScreen);
	}


	@Override
	public void render () {

		double alpha = 0.0;

		if (screen == gameScreen) {
			// fixed timestep consumes time from accumulator in DT sized chunks
			accumulator += Gdx.graphics.getDeltaTime();
			while (accumulator >= DT) {
				gameScreen.update((float) DT);
				accumulator -= DT;
				t += DT;
			}

			alpha = accumulator / DT;
		}
		// clear window
		Gdx.gl.glClearColor(0.03f, 0.03f, 0.03f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (screen == gameScreen)
			gameScreen.render((float) t, (float) DT, (float) alpha);
		else if (screen == titleScreen)
			titleScreen.render((float) t, (float) DT, (float) alpha);
		else if (screen == creditsScreen)
			creditsScreen.render((float) t, (float) DT, (float) alpha);

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
