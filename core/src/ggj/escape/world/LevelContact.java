package ggj.escape.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import ggj.escape.components.BulletComponent;
import ggj.escape.components.PhysicsComponent;

import java.util.ArrayList;

/**
 * Created by joshua on 1/24/15.
 */
public class LevelContact implements ContactListener {

    private Level level;

    public LevelContact(Level level) {
        this.level = level;
    }

    @Override
    public void beginContact(Contact contact) {

        ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
        fixtures.add(contact.getFixtureA());
        fixtures.add(contact.getFixtureB());

        for (Fixture f : fixtures) {
            if (f.getUserData() != null && f.getUserData().getClass() == Entity.class) {
                Entity e = (Entity) f.getUserData();
                if (e.getComponent(BulletComponent.class) != null) {
                    level.toRemove.add(e);
                }
            }
        }


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
