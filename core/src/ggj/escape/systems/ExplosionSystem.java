package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import ggj.escape.components.CharacterComponent;
import ggj.escape.components.ExplosionComponent;
import ggj.escape.components.Mappers;
import ggj.escape.components.SpriteComponent;


public class ExplosionSystem extends IteratingSystem {

    private Engine engine;

    public ExplosionSystem() {
        super(Mappers.families.fx);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ExplosionComponent fx = Mappers.fx.get(entity);
        SpriteComponent sp = Mappers.sprite.get(entity);

        if (sp.animation.isAnimationFinished(sp.stateTime)) {
            engine.removeEntity(entity);
        }

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }
}
