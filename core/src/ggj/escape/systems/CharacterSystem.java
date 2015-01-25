package ggj.escape.systems;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import ggj.escape.components.CharacterComponent;
import ggj.escape.components.Mappers;


public class CharacterSystem extends IteratingSystem {

    private Engine engine;

    public CharacterSystem() {
        super(Mappers.families.characters);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
//        this.world = engine.getSystem(PhysicsSystem.class).world;
    }

    public void hurt(Entity me, Entity other) {

        CharacterComponent character = Mappers.character.get(me);

        character.hp--;

        if (character.hp <= 0) {
            die(me, other);
        }
    }

    public void die(Entity me, Entity other) {

        CharacterComponent character = Mappers.character.get(me);
        engine.getSystem(PhysicsSystem.class).toRemove.add(me);
        character.alive = false;

    }


}
