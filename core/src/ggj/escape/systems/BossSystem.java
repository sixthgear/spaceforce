package ggj.escape.systems;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import ggj.escape.components.BaddieComponent;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;


public class BossSystem extends BaddieSystem {

    public BossSystem() {
        super(Mappers.families.boss, 1 / 4f);
    }

    @Override
    protected void processEntity(Entity entity) {

        if (world == null)
            return;

        ImmutableArray<Entity> players = engine.getEntitiesFor(Mappers.families.players);

        BaddieComponent baddie = Mappers.enemy.get(entity);
        PhysicsComponent physics = Mappers.physics.get(entity);
        Vector2 baddiePos = physics.body.getPosition();

//        if (baddie.target != null) {
//
//            Vector2 targetPos = Mappers.physics.get(baddie.target).body.getPosition();
//            Vector2 delta = targetPos.cpy().sub(baddiePos);
//
//            if (delta.len2() > baddie.sight) {
//
//                BaddieCanSee canSee = new BaddieCanSee(baddie.target);
//                world.rayCast(canSee, baddiePos, targetPos);
//                if (!canSee.found)
//                    baddie.target = null;
//
//            }
//
//            physics.body.setLinearVelocity(delta.setLength(baddie.speed));
//
//        } else {
//
//            // search
//            for (Entity p : players) {
//
//                Vector2 targetPos = Mappers.physics.get(p).body.getPosition();
//
//                if (baddiePos.cpy().sub(targetPos).len2() > baddie.sight)
//                    continue;
//
//                BaddieCanSee canSee = new BaddieCanSee(p);
//                world.rayCast(canSee, baddiePos, targetPos);
//                if (canSee.found)
//                    baddie.target = canSee.target;
//
//            }
//        }

    }

    public void hit(Entity other) {

    }


}
