package ggj.escape.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;
import ggj.escape.components.SpriteComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    private SpriteBatch batch = new SpriteBatch();

    public RenderSystem() {
        super(Mappers.families.sprites, new YComparator());
    }

    private static class YComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            PhysicsComponent ph1 = Mappers.physics.get(e1);
            PhysicsComponent ph2 = Mappers.physics.get(e2);
            return (int)Math.signum(ph1.body.getPosition().y - ph2.body.getPosition().y);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void interpolate(float delta, float alpha) {

        for (Entity entity : getEntities()) {
        // interpolate sprites according to frame alpha

            SpriteComponent sprite = Mappers.sprite.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);

            Vector2 pos = physics.body.getPosition();
            Vector2 interpolated = sprite.last.cpy().interpolate(pos, alpha, Interpolation.linear);
            sprite.x = (pos.x) - 0.5f; //interpolated.x - 16;
            sprite.y = (pos.y) - 0.5f; //interpolated.y - 16;

            sprite.last = pos.cpy();
        }
    }

    public void render(Camera camera) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : getEntities()) {

            SpriteComponent sprite = Mappers.sprite.get(entity);
            if (sprite.animation != null) {
                sprite.stateTime += Gdx.graphics.getDeltaTime();
                batch.draw(sprite.animation.getKeyFrame(sprite.stateTime, true), sprite.x, sprite.y, sprite.w, sprite.h);
            } else {
                batch.draw(sprite.region, sprite.x, sprite.y, sprite.w, sprite.h);
            }

        }
        batch.end();
    }

}
