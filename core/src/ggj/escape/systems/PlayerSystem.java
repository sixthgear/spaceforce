package ggj.escape.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import ggj.escape.Resources;
import ggj.escape.components.*;
import ggj.escape.input.XBox360Pad;
import ggj.escape.world.Level;

public class PlayerSystem extends EntitySystem  implements ControllerListener {

    private Engine engine;
    private World world;

    public PlayerSystem() {
        super();
        Controllers.addListener(this);
    }

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Mappers.families.players);
        this.engine = engine;
        this.world = engine.getSystem(PhysicsSystem.class).world;
    }


    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }

    public Entity createPlayer(int role, int x, int y) {

        Entity player = new Entity();
        PhysicsSystem ph = engine.getSystem(PhysicsSystem.class);

        player.add(new PlayerComponent(role));
        player.add(new CharacterComponent(20));
        player.add(new PhysicsComponent(ph.createCircBody(x, y, 0.48f, PlayerComponent.category, PlayerComponent.mask)));
        player.add(new SpriteComponent(player.getComponent(PlayerComponent.class).regions.get(0)));
        return player;
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {

            Controller controller = Controllers.getControllers().get(i);

            Entity entity = entities.get(i);
            PhysicsComponent p = Mappers.physics.get(entity);
            SpriteComponent s = Mappers.sprite.get(entity);
            CharacterComponent c = Mappers.character.get(entity);
            PlayerComponent pl = Mappers.player.get(entity);

            Vector2 pos = p.body.getPosition();
            Vector2 movement = new Vector2();
//            Vector2 aiming = new Vector2();


            float trigger;

            movement.x = controller.getAxis(XBox360Pad.AXIS_LEFT_X);
            movement.y = controller.getAxis(XBox360Pad.AXIS_LEFT_Y) * -1;
            pl.aiming.x = controller.getAxis(XBox360Pad.AXIS_RIGHT_X);
            pl.aiming.y = controller.getAxis(XBox360Pad.AXIS_RIGHT_Y) * -1;
            trigger = controller.getAxis(XBox360Pad.AXIS_RIGHT_TRIGGER);

            // deadzone
            if (movement.len2() > 0.4) {
                movement.scl(4.5f);
                p.body.setLinearVelocity(movement);
                p.body.setLinearDamping(0f);
            } else {
                p.body.setLinearDamping(10f);
            }

            pl.isAiming = (pl.aiming.len2() > 0.4);

            if (trigger > 0.3 && c.cooldown <= 0) {

                if (!pl.isAiming)
                    pl.aiming = movement.cpy();

                Vector2 bulletPos = pos.cpy().add(pl.aiming.cpy().setLength2(0.125f));
                bulletPos.y += 0.75;
                Entity bullet = new Entity();
                PhysicsSystem ph = engine.getSystem(PhysicsSystem.class);
                Body body = ph.createCircBody(bulletPos.x, bulletPos.y, 0.0625f, BulletComponent.category, BulletComponent.mask);
                PhysicsComponent b = new PhysicsComponent(body);

                bullet.add(b);
                bullet.add(new SpriteComponent(Resources.sprites.bullet));
                bullet.add(new BulletComponent());
                engine.addEntity(bullet);

                b.body.setBullet(true);
                b.body.setLinearVelocity(pl.aiming.setLength(24));
                b.body.setLinearDamping(0);
                Fixture f = b.body.getFixtureList().first();
                f.setSensor(true);

                float r = MathUtils.random();

                if (r < 0.7)
                    Resources.sfx.pistol_1.play(0.25f);
                else if (r < 0.9)
                    Resources.sfx.pistol_2.play(0.25f);
                else
                    Resources.sfx.pistol_3.play(0.25f);

                c.cooldown = c.maxCooldown;
            }

            if (pl.isAiming)
                pl.angleAim = pl.aiming.angle();
            else
                pl.angleAim = movement.angle();

            // change torso angle
            if (pl.angleAim > 337.5 || pl.angleAim <= 22.5)
                s.region = pl.regions.get(2);
            if (pl.angleAim > 22.5  && pl.angleAim <= 67.5)
                s.region = pl.regions.get(1);
            if (pl.angleAim > 67.5  && pl.angleAim <= 112.5)
                s.region = pl.regions.get(0);
            if (pl.angleAim > 112.5 && pl.angleAim <= 157.5)
                s.region = pl.regions.get(7);
            if (pl.angleAim > 157.5 && pl.angleAim <= 202.5)
                s.region = pl.regions.get(6);
            if (pl.angleAim > 202.5 && pl.angleAim <= 247.5)
                s.region = pl.regions.get(5);
            if (pl.angleAim > 247.5 && pl.angleAim <= 292.5)
                s.region = pl.regions.get(4);
            if (pl.angleAim > 292.5 && pl.angleAim <= 337.5)
                s.region = pl.regions.get(3);


            c.cooldown--;

        }
    }

    // connected and disconnect dont actually appear to work for XBox 360 controllers.
    @Override
    public void connected(Controller controller) {
        System.out.println("Controlled Connected");
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("Controlled Disconnected");
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {

        if(buttonCode == XBox360Pad.BUTTON_Y)
            System.out.println("Y");
        else if(buttonCode == XBox360Pad.BUTTON_A)
            System.out.println("A");
        else if(buttonCode == XBox360Pad.BUTTON_X)
            System.out.println("X");
        else if(buttonCode == XBox360Pad.BUTTON_B)
            System.out.println("B");
        else if(buttonCode == XBox360Pad.BUTTON_LB)
            System.out.println("LB");
        else if(buttonCode == XBox360Pad.BUTTON_RB)
            System.out.println("RB");
        else if(buttonCode == XBox360Pad.BUTTON_L3)
            System.out.println("L3");
        else if(buttonCode == XBox360Pad.BUTTON_R3)
            System.out.println("R3");
        else if(buttonCode == XBox360Pad.BUTTON_GUIDE)
            System.out.println("GUIDE");
        else if(buttonCode == XBox360Pad.BUTTON_BACK)
            System.out.println("BACK");
        else if(buttonCode == XBox360Pad.BUTTON_START)
            System.out.println("START");
        else if(buttonCode == XBox360Pad.BUTTON_DPAD_LEFT)
            System.out.println("L");
        else if(buttonCode == XBox360Pad.BUTTON_DPAD_RIGHT)
            System.out.println("R");
        else if(buttonCode == XBox360Pad.BUTTON_DPAD_UP)
            System.out.println("U");
        else if(buttonCode == XBox360Pad.BUTTON_DPAD_DOWN)
            System.out.println("D");
        else
            System.out.println(buttonCode);

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
        // This is the dpad
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

}
