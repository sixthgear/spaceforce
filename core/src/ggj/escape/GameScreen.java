package ggj.escape;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import ggj.escape.components.*;

import ggj.escape.systems.*;
import ggj.escape.world.Level;


public class GameScreen extends ScreenAdapter {

    public Level level;
    public Engine engine;
    public PooledEngine pool;

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    // references to important entities
    public Entity camera;

    private boolean debug = false;

    public SpriteBatch uiBatch;
    public SpriteBatch hudBatch;

    @Override
    public void show() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // created the pooled engine
        pool = new PooledEngine(32, 2048, 2, 8);

        // create the main engine
        engine = new Engine();

        // add systems
        engine.addSystem(new PhysicsSystem(engine));
        engine.addSystem(new RenderSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new CharacterSystem());
        engine.addSystem(new BaddieSystem());
        engine.addSystem(new PlayerSystem(pool, engine));

        engine.addEntityListener(Mappers.families.physics, engine.getSystem(PhysicsSystem.class));

        // create the level
        level = new Level(engine);

        // count connected controllers
        int numPlayers = Controllers.getControllers().size;

        // add one player for each controller
        for (int i = 0; i < numPlayers; i++) {
            Entity player = engine.getSystem(PlayerSystem.class).createPlayer(level, i);
            engine.addEntity(player);
        }

        // add the main camera entity
        camera = new Entity();
        camera.add(new CameraComponent(w, h, new Vector2(1.0f, 1.0f), level, engine.getEntitiesFor(Mappers.families.players)));
        engine.addEntity(camera);

        uiBatch = new SpriteBatch(1000);
        hudBatch = new SpriteBatch(1000);
        Resources.fonts.roboto_white_cache.addText("ROBOTS!", 10, 10);

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
                    case Input.Keys.Q:
                        debug = !debug;
                        return true;
                    case Input.Keys.F:
                        level.dropTheBass("music/Escape_From_the_Lab_BG_Music_Loop_Jasmine.mp3", "", 0.0f);
                        return true;
                    case Input.Keys.D:
                        level.dropTheBass(
                            "music/Escape_From_the_Lab_BG_Music_Boss_Init_Jeremy-Lim.mp3",
                            "music/Escape_From_the_Lab_BG_Music_Boss_Loop_Jeremy-Lim.mp3",
                            1.0f);
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

    public void render(float time, float delta, float alpha) {

        RenderSystem renderSystem = engine.getSystem(RenderSystem.class);
        CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);
        PhysicsSystem physicsSystem = engine.getSystem(PhysicsSystem.class);
        OrthographicCamera cam = Mappers.camera.get(camera).camera;

        // interpolate all sprites according to alpha
        renderSystem.interpolate(delta, alpha);

        // scroll cameras (important to do this here to account for interpolated sprites)
        cameraSystem.follow();

        // render the level
        level.render(cam);

        // render engine entities
        renderSystem.render(cam);

        // render the level overlay
        level.renderOverlay(cam);

        uiBatch.setProjectionMatrix(cam.combined);
        uiBatch.begin();

        for (Entity p : engine.getEntitiesFor(Mappers.families.players)) {

            SpriteComponent sp = Mappers.sprite.get(p);
            PlayerComponent pl = Mappers.player.get(p);

            uiBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            if (pl.isAiming) {

                Sprite crosshair = Resources.sprites.crosshair[pl.role];
                crosshair.setX(sp.x);
                crosshair.setY(sp.y + 1f);
                crosshair.setRotation(pl.angleAim);
                crosshair.draw(uiBatch);


            }
        }
        uiBatch.end();

        hudBatch.setProjectionMatrix(cam.view);
        hudBatch.begin();
        Resources.fonts.roboto_white_cache.draw(hudBatch);
        hudBatch.end();


        if (debug) {
            level.renderDebug(cam);
            debugRenderer.render(physicsSystem.world, cam.combined);
        }

    }

}
