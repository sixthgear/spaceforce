package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;
import ggj.escape.components.PlayerComponent;
import ggj.escape.components.SpriteComponent;

public class PlayerSystem extends EntitySystem {

    private Family family = Family.getFor(PlayerComponent.class);
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            PhysicsComponent p = Mappers.physics.get(entity);
            SpriteComponent s = Mappers.sprite.get(entity);
            PlayerComponent pl = Mappers.player.get(entity);

            // sample input for this frame
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                p.acc.x = -500;
                s.region = pl.regionLeft;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                p.acc.x = 500;
                s.region = pl.regionRight;
            } else {
                p.acc.x = 0;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                p.acc.y = 500;
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                p.acc.y = -500;
            else
                p.acc.y = 0;

        }
    }

}
