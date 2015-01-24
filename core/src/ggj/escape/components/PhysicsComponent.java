package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsComponent extends Component{

    public Body body;

    public PhysicsComponent(World world, int x, int y, int width, int height) {

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(16);

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.density = 0.5f;
        fixturedef.friction = 0.4f;

        body = world.createBody(bodydef);
        Fixture fixture = body.createFixture(fixturedef);
        shape.dispose();

    }
}
