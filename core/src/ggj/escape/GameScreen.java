package ggj.escape;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.*;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import ggj.escape.components.*;
import ggj.escape.systems.*;
import ggj.escape.world.Level;


public class GameScreen extends ScreenAdapter {

    public Level level;
    public Engine engine ;

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public int numPlayers;
    // references to important entities
    public Entity camera;
    public OrthographicCamera hud;

    private boolean debug = false;

    public SpriteBatch uiBatch;
    public SpriteBatch hudBatch;
    public EscapeGame game;

    public final int WIDTH = 1280;
    public final int HEIGHT = 800;

    public GameScreen(EscapeGame game ) {
        this.game = game;
    }

    public void init() {

        // create the main engine
        engine = new Engine();

        // add systems
        engine.addSystem(new PhysicsSystem(engine));
        engine.addSystem(new RenderSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new CharacterSystem());
        engine.addSystem(new ExplosionSystem());
        engine.addSystem(new BaddieSystem());
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new TriggerSystem());
        engine.addSystem(new BossSystem());

        engine.addEntityListener(Mappers.families.physics, engine.getSystem(PhysicsSystem.class));

        // count connected controllers
        numPlayers = Math.max(Controllers.getControllers().size, 1);

    }

    @Override
    public void show() {

        hud = new OrthographicCamera(WIDTH, HEIGHT);
        hud.setToOrtho(false, 40, 25);
        uiBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();

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

                    case Input.Keys.N:
                        Entity ex = engine.getEntitiesFor(Mappers.families.exits).first();
                        if (ex != null){
                            Mappers.exit.get(ex).triggered = true;
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

        // setup engine
        init();

        // create the level
        level = new Level(engine, numPlayers);

        // add the main camera entity
        camera = new Entity();
        camera.add(new CameraComponent(WIDTH, HEIGHT, new Vector2(1.0f, 1.0f), level, engine.getEntitiesFor(Mappers.families.players)));
        engine.addEntity(camera);
    }

    public void update(float delta) {

        // update the engine
        engine.update(delta);

        for (Entity e : engine.getEntitiesFor(Mappers.families.exits)) {

            if (Mappers.exit.get(e).triggered) {
                System.out.println("NEXT!");
                level.music.stop();
                level.music.dispose();
                engine.removeAllEntities();

                for (EntitySystem s : engine.getSystems()) {
                    engine.removeSystem(s);
                }

                init();

                String next = Mappers.exit.get(e).nextLevel;

                if (next.equals("end-credits")) {
                    game.setScreen(game.creditsScreen);
                    return;
                } else {

                    level = new Level(engine, next, numPlayers);

                    // add the main camera entity
                    camera = new Entity();
                    camera.add(new CameraComponent(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector2(1.0f, 1.0f), level, engine.getEntitiesFor(Mappers.families.players)));
                    engine.addEntity(camera);
                }
            }
        }

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

        hudBatch.setProjectionMatrix(hud.combined);
        hudBatch.begin();

        Resources.fonts.roboto_white_cache.draw(hudBatch);

        for (Entity p : engine.getEntitiesFor(Mappers.families.players)) {
            SpriteComponent sp = Mappers.sprite.get(p);
            PlayerComponent pl = Mappers.player.get(p);
            CharacterComponent ch = Mappers.character.get(p);

            float f = (float) ch.hp / ch.maxHp;
            int w = 32 + (int) (224.0f * f);
            TextureRegion tr = new TextureRegion(Resources.sprites.health[pl.role], 0, 0, w, 32);

            hudBatch.draw(tr, 0.5f + pl.role * 9, 23.5f, 1 + 7 * f, 1);

            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 mouse = hud.unproject(mousePos);
//            System.out.println(mouse);
            hudBatch.draw(Resources.sprites.mouse, mouse.x - 1f, mouse.y - 1f, 2, 2);

        }

        hudBatch.end();


        if (debug) {
            level.renderDebug(cam);
            debugRenderer.render(physicsSystem.world, cam.combined);
        }

    }

}
