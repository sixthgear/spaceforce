package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsComponent extends Component{


    public Body body;

    public PhysicsComponent(World world, float x, float y, float width, float height, short group) {

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(width);

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.filter.groupIndex = group;
//        fixturedef.filter.maskBits

        body = world.createBody(bodydef);
        body.setFixedRotation(true);

        Fixture fixture = body.createFixture(fixturedef);

        shape.dispose();

    }
}
