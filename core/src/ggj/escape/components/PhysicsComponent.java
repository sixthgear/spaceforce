package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsComponent extends Component{

    public Body body;

    public PhysicsComponent(Body body) {
        this.body = body;
    }
}
