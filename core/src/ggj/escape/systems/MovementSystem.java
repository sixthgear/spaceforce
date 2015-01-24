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

    }

}
