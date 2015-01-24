package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.components.CameraComponent;
import ggj.escape.components.Mappers;

public class CameraSystem extends EntitySystem {

    private Family family = Family.getFor(CameraComponent.class);
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }

    public void follow() {

        // update cameras to follow their targets
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            CameraComponent c = Mappers.camera.get(entity);
//            SpriteComponent s = Mappers.sprite.get(c.target);
            Vector2 avg = new Vector2();
            for (int j = 0; j < c.targets.size(); j++) {
                avg.add(Mappers.physics.get(c.targets.get(j)).body.getPosition());
            }
            avg.scl(1.0f / c.targets.size());


            // conform scolling to bounds
            float lb = c.camera.viewportWidth / 2;
            float rb = c.level.width * c.level.TILESIZE - lb;
            float bb = c.camera.viewportHeight / 2;
            float tb = c.level.height * c.level.TILESIZE - bb;

            // clamp to edges
            float x = MathUtils.clamp((avg.x - lb) * c.scale.x + lb, lb, (rb - lb) * c.scale.x + lb);
            float y = MathUtils.clamp((avg.y - bb) * c.scale.y + bb, bb, (tb - bb) * c.scale.y + bb);

            // update positions
            c.camera.position.set(x, y, 0);
            c.camera.update();
        }
    }

}
