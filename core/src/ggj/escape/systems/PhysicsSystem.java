package ggj.escape.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ObjectSet;
import ggj.escape.components.CharacterComponent;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;

import java.util.ArrayList;


public class PhysicsSystem extends EntitySystem implements ContactListener, EntityListener {


    public Body createBody(float x, float y, float radius, short category, short mask) {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.filter.categoryBits = category;
        fixturedef.filter.maskBits = mask;

        Body body = world.createBody(bodydef);
        body.setFixedRotation(true);

        body.createFixture(fixturedef);

        shape.dispose();
        return body;
    }

    @Override
    public void entityAdded(Entity entity) {
        PhysicsComponent ph = Mappers.physics.get(entity);
        ph.body.getFixtureList().first().setUserData(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }

    public World world;
    public ObjectSet<Entity> toRemove = new ObjectSet<>();
    private Engine engine;
    private ImmutableArray<Entity> entities;

    public PhysicsSystem(Engine engine) {
        this.world = new World(Vector2.Zero, true);
        this.world.setContactListener(this);
        this.engine = engine;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(PhysicsComponent.class));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // step the world
        world.step(deltaTime, 6, 2);

        // remove dead entities
        for (Entity e : toRemove) {
            PhysicsComponent ph = Mappers.physics.get(e);
            ph.body.destroyFixture(ph.body.getFixtureList().first());
            world.destroyBody(ph.body);
            engine.removeEntity(e);
        }

        toRemove.clear();

    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();
        Entity eA = (Entity) fA.getUserData();
        Entity eB = (Entity) fB.getUserData();

        if (eA != null) {
            if (Mappers.bullet.get(eA) != null)
                toRemove.add(eA);
        }

        if (eB != null) {
            if (Mappers.bullet.get(eB) != null)
                toRemove.add(eB);
        }

//        System.out.print(eA);
//        System.out.print(" vs ");
//        System.out.println(eB);

        if (eA == null || eB == null)
            return;

        // bullet hits character
        if (Mappers.families.characters.matches(eA) && Mappers.families.bullets.matches(eB))
            engine.getSystem(CharacterSystem.class).hurt(eA, eB);

        // bullet hits character
        else if (Mappers.families.characters.matches(eB) && Mappers.families.bullets.matches(eA))
            engine.getSystem(CharacterSystem.class).hurt(eB, eA);

        // baddie hits player
        else if (Mappers.families.players.matches(eA) && Mappers.families.baddies.matches(eB))
            engine.getSystem(CharacterSystem.class).hurt(eA, eB);

        // baddie hits player
        else if (Mappers.families.players.matches(eB) && Mappers.families.baddies.matches(eA))
            engine.getSystem(CharacterSystem.class).hurt(eB, eA);

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