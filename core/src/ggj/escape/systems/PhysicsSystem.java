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

    public World world;
    public ObjectSet<Entity> toRemove = new ObjectSet<>();
    private Engine engine;
    private ImmutableArray<Entity> entities;

    public PhysicsSystem(Engine engine) {
        this.world = new World(Vector2.Zero, true);
        this.world.setContactListener(this);
        this.engine = engine;
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);

        System.out.println("Destroying World.");
        world.clearForces();
        world.dispose();
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(PhysicsComponent.class));
    }

    public Body createCircBody(float x, float y, float radius, short category, short mask) {

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);
        bodydef.fixedRotation = true;

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.filter.categoryBits = category;
        fixturedef.filter.maskBits = mask;

        Body body = world.createBody(bodydef);
        body.createFixture(fixturedef);
        shape.dispose();
        return body;
    }

    public Body createBoxBody(float x, float y, float width, float height, short category, short mask) {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);
        bodydef.fixedRotation = true;
        bodydef.linearDamping = 2.0f;

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.density = 40.0f;
        fixturedef.friction = 10.0f;
        fixturedef.filter.categoryBits = category;
        fixturedef.filter.maskBits = mask;

        Body body = world.createBody(bodydef);
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

        // character hits trigger
        else if (Mappers.families.triggers.matches(eA) && Mappers.families.characters.matches(eB))
            engine.getSystem(TriggerSystem.class).enter(eA, eB);

        // character hits trigger
        else if (Mappers.families.triggers.matches(eB) && Mappers.families.characters.matches(eA))
            engine.getSystem(TriggerSystem.class).enter(eB, eA);


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