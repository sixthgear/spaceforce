package ggj.escape;


import com.badlogic.gdx.*;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class TitleScreen extends ScreenAdapter implements ControllerListener {
    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        game.setScreen(game.gameScreen);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    public OrthographicCamera camera;
    public SpriteBatch uiBatch;
    public EscapeGame game;

    public TitleScreen(EscapeGame game) {
        super();
        this.game = game;
        Controllers.addListener(this);
    }

    @Override
    public void show() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        camera = new OrthographicCamera(w, h);
        camera.setToOrtho(false, w, h);
        uiBatch = new SpriteBatch(1000);
        Resources.fonts.roboto_white_cache.addText("ROBOTS!", 10, 10);

        // set up main window input handling
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean keyDown (int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        Gdx.app.exit();
                        return true;
                    case Input.Keys.ENTER:
                        game.setScreen(game.gameScreen);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    public void update(float delta) {


    }

    public void render(float time, float delta, float alpha) {

        uiBatch.setProjectionMatrix(camera.combined);

        uiBatch.begin();
        uiBatch.draw(Resources.screens.title, 0, 0);
        Resources.fonts.roboto_white_cache.draw(uiBatch);
        uiBatch.end();

    }

}
