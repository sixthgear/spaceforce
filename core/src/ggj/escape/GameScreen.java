package ggj.escape;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;

import ggj.escape.components.*;

import ggj.escape.systems.CameraSystem;
import ggj.escape.systems.MovementSystem;
import ggj.escape.systems.PlayerSystem;
import ggj.escape.systems.RenderSystem;
import ggj.escape.world.Level;


public class GameScreen extends ScreenAdapter {

    public Level level;
    public Engine engine;

    // references to important entities
    public Family players = Family.getFor(PlayerComponent.class);
    public Entity camera;

    @Override
    public void show() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // create the engine
        engine = new Engine();
        engine.addSystem(new PlayerSystem());
//        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new CameraSystem());

        // create the world
        level = new Level(engine);

        int numPlayers = Controllers.getControllers().size;

        // add the player entities
        for (int i = 0; i < numPlayers; i++) {
            Entity player = new Entity();
            PlayerComponent p = new PlayerComponent();
            player.add(p);
            player.add(new PhysicsComponent(level.world, 256 + 128 * i, 1440, 32, 64));
            player.add(new SpriteComponent(p.regions.get(i)));

            engine.addEntity(player);
        }

        // add the main camera entity
        camera = new Entity();
        camera.add(new CameraComponent(h, w, new Vector2(1.0f, 1.0f), level, engine.getEntitiesFor(players)));
        engine.addEntity(camera);

        // set up input handling
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean keyDown (int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        Gdx.app.exit();
                        return true;
                    case Input.Keys.NUM_1:
                        level.toggleLayer(0);
                        return true;
                    case Input.Keys.NUM_2:
                        level.toggleLayer(1);
                        return true;
                    case Input.Keys.NUM_3:
                        level.toggleLayer(2);
                        return true;
                    case Input.Keys.NUM_4:
                        level.toggleLayer(3);
                        return true;
                    case Input.Keys.NUM_5:
                        level.toggleLayer(4);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void update(float delta) {

        // update the engine
        engine.update(delta);

        // update the world
        level.update(delta);

    }

    public void render(float delta, float alpha) {

        RenderSystem renderSystem = engine.getSystem(RenderSystem.class);
        CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);

        // interpolate all sprites according to alpha
        renderSystem.interpolate(delta, alpha);

        // scroll cameras (important to do this here to account for interpolated sprites)
        cameraSystem.follow();

        level.render(Mappers.camera.get(camera).camera);

        // render engine entities
        renderSystem.render(Mappers.camera.get(camera).camera);

    }




}
