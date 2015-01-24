package ggj.escape;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.components.*;
import ggj.escape.systems.CameraSystem;
import ggj.escape.systems.MovementSystem;
import ggj.escape.systems.PlayerSystem;
import ggj.escape.systems.RenderSystem;
import ggj.escape.world.World;

public class GameScreen extends ScreenAdapter {

    public World world;
    public Engine engine;

    // references to important entities
    public Entity player;
    public Entity camera;
    public Entity backgroundCamera;

    @Override
    public void show() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // create the engine
        engine = new Engine();
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new CameraSystem());

        // create the world
        world = new World(engine);

        // add the player entity
        player = new Entity();
        PlayerComponent p = new PlayerComponent();
        player.add(p);
        player.add(new PhysicsComponent(48, 60));
        player.add(new SpriteComponent(p.regionRight));
        player.getComponent(PhysicsComponent.class).pos0.x = -2.0f;
        player.getComponent(PhysicsComponent.class).pos0.y = -3.0f;
        engine.addEntity(player);

        // add the main camera entity
        camera = new Entity();
        camera.add(new CameraComponent(h, w, new Vector2(1.0f, 1.0f), world, player));
        engine.addEntity(camera);

        // add the background camera entity
        backgroundCamera = new Entity();
        backgroundCamera.add(new CameraComponent(h, w, new Vector2(0.5f, 0.0f), world, player));
        engine.addEntity(backgroundCamera);

        // set up input handling
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean keyDown (int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        Gdx.app.exit();
                        return true;
                    case Input.Keys.NUM_1:
                        world.toggleLayer(0);
                        return true;
                    case Input.Keys.NUM_2:
                        world.toggleLayer(1);
                        return true;
                    case Input.Keys.NUM_3:
                        world.toggleLayer(2);
                        return true;
                    case Input.Keys.NUM_4:
                        world.toggleLayer(3);
                        return true;
                    case Input.Keys.NUM_5:
                        world.toggleLayer(4);
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
//        world.update(delta);

    }

    public void render(float delta, float alpha) {

        RenderSystem renderSystem = engine.getSystem(RenderSystem.class);
        CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);

        // interpolate all sprites according to alpha
        renderSystem.interpolate(delta, alpha);

        // scroll cameras (important to do this here to account for interpolated sprites)
        cameraSystem.follow();

        // render world and tiles
//        world.renderBack(Mappers.camera.get(backgroundCamera).camera);
//        world.render(Mappers.camera.get(camera).camera);

        // render engine entities
        renderSystem.render(Mappers.camera.get(camera).camera);

        // render world top
//        world.renderTop(Mappers.camera.get(camera).camera);
    }
}
