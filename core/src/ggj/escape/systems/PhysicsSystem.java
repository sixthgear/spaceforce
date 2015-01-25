package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import ggj.escape.components.PhysicsComponent;


public class PhysicsSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(PhysicsComponent.class));

        System.out.println("Added PHYSOCS");
    }



}
