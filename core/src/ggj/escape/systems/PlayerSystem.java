package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;
import ggj.escape.components.PlayerComponent;
import ggj.escape.components.SpriteComponent;
import ggj.escape.input.XBox360Pad;

public class PlayerSystem extends EntitySystem  implements ControllerListener {

    public Family family = Family.getFor(PlayerComponent.class);

    public PlayerSystem() {
        super();
        Controllers.addListener(this);
    }

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            PhysicsComponent p = Mappers.physics.get(entity);
            SpriteComponent s = Mappers.sprite.get(entity);
            PlayerComponent pl = Mappers.player.get(entity);
            
            float x = Controllers.getControllers().get(i).getAxis(XBox360Pad.AXIS_LEFT_X);
            float y = Controllers.getControllers().get(i).getAxis(XBox360Pad.AXIS_LEFT_Y);

            if (Math.abs(x) < 0.1)  x = 0;
            if (Math.abs(y) < 0.1)  y = 0;

            p.acc.x = x * 200;
            p.acc.y = y * -200;

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
        // This is your analog stick
        // Value will be from -1 to 1 depending how far left/right, up/down the stick is
        // For the Y translation, I use a negative because I like inverted analog stick
        // Like all normal people do! ;)

//        System.out.println(axisCode);
//        // Left Stick
//        if(axisCode == XBox360Pad.AXIS_LEFT_X)
//            System.out.printf("%d: %f\n", axisCode, value);
//        if(axisCode == XBox360Pad.AXIS_LEFT_Y)
//            System.out.printf("%d: %f\n", axisCode, value);
//
//        // Right stick
//        if(axisCode == XBox360Pad.AXIS_RIGHT_X)
//            System.out.printf("%d: %f\n", axisCode, value);
//        if(axisCode == XBox360Pad.AXIS_RIGHT_Y)
//            System.out.printf("%d: %f\n", axisCode, value);

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
