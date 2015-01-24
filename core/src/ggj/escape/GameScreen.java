package ggj.escape;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;

import ggj.escape.components.*;

import ggj.escape.systems.CameraSystem;
import ggj.escape.systems.PlayerSystem;
import ggj.escape.systems.RenderSystem;
import ggj.escape.world.Level;


public class GameScreen extends ScreenAdapter {

    public Level level;
    public Engine engine;
    public PooledEngine pool;

    // references to important entities
    public Family players = Family.getFor(PlayerComponent.class);
    public Family enemies = Family.getFor(EnemyComponent.class);
    public Entity camera;

    @Override
    public void show() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // created the pooled engine
        pool = new PooledEngine(32, 2048, 2, 8);

        // create the main engine
        engine = new Engine();

        // create the level
        level = new Level(engine);

        // add systems
        engine.addSystem(new PlayerSystem(pool, engine, level.world));
        engine.addSystem(new RenderSystem());
        engine.addSystem(new CameraSystem());

        // count connected controllers
        int numPlayers = Controllers.getControllers().size;

        // add one player for each controller
        for (int i = 0; i < numPlayers; i++) {
            Entity player = new Entity();
            PlayerComponent p = new PlayerComponent();
            player.add(p);
            player.add(new PhysicsComponent(level.world, 8 + 5 * i, 45, 0.5f, 0.5f, (short) 1));
            player.add(new SpriteComponent(p.regions.get(i)));
            engine.addEntity(player);
        }

        // add the main camera entity
        camera = new Entity();
        camera.add(new CameraComponent(w, h, new Vector2(1.0f, 1.0f), level, engine.getEntitiesFor(players)));
        engine.addEntity(camera);

        // set up main window input handling
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
                    case Input.Keys.D:
                        level.toggleDebug();
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

        // update the level
        level.update(delta);

    }

    public void render(float delta, float alpha) {

        RenderSystem renderSystem = engine.getSystem(RenderSystem.class);
        CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);

        // interpolate all sprites according to alpha
        renderSystem.interpolate(delta, alpha);

        // scroll cameras (important to do this here to account for interpolated sprites)
        cameraSystem.follow();

        // render the level
        level.render(Mappers.camera.get(camera).camera);

        // render engine entities
        renderSystem.render(Mappers.camera.get(camera).camera);

        // render the level overlay
        level.renderOverlay(Mappers.camera.get(camera).camera);

    }

}
