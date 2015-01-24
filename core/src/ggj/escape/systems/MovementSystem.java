package ggj.escape.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.getFor(PhysicsComponent.class));
    }

    @Override
    public void processEntity(Entity entity, float dt) {

        PhysicsComponent physics = Mappers.physics.get(entity);

        // save temporary position
        Vector2 p = physics.pos.cpy();

        // integrate verlet
        float dt2 = dt * dt;
        Vector2 delta = physics.pos.cpy().sub(physics.pos0);
        delta.scl(physics.damping);
        physics.pos.add(delta);
        physics.pos.mulAdd(physics.acc, dt2);

        // save previous position
        physics.pos0.x = p.x;
        physics.pos0.y = p.y;

    }

}
