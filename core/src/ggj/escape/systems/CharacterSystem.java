package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import ggj.escape.Resources;
import ggj.escape.components.*;


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
        SpriteComponent sprite = Mappers.sprite.get(me);

        if (Family.getFor(SpiderComponent.class).matches(me)){
            Entity explosion = new Entity();
            explosion.add(new ExplosionComponent(10));
            SpriteComponent sp = new SpriteComponent(Resources.animations.spider.explode);
            sp.x = sprite.x - 1f;
            sp.y = sprite.y - 1f;
            explosion.add(sp);
            engine.addEntity(explosion);
        } else if (Family.getFor(RobotComponent.class).matches(me)){
            Entity explosion = new Entity();
            explosion.add(new ExplosionComponent(10));
            SpriteComponent sp = new SpriteComponent(Resources.animations.robot.explode);
            sp.x = sprite.x - 3f;
            sp.y = sprite.y - 1.5f;
            explosion.add(sp);
            engine.addEntity(explosion);
        } else if (Family.getFor(SlimeComponent.class).matches(me)){
            Entity explosion = new Entity();
            explosion.add(new ExplosionComponent(10));
            SpriteComponent sp = new SpriteComponent(Resources.animations.slime.explode);
            sp.x = sprite.x  - 1f;
            sp.y = sprite.y  - 1f;
            explosion.add(sp);
            engine.addEntity(explosion);
        }

        character.alive = false;
        engine.getSystem(PhysicsSystem.class).toRemove.add(me);

    }


}
