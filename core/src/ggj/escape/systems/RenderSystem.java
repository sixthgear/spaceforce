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
            SpriteComponent sp1 = Mappers.sprite.get(e1);
            SpriteComponent sp2 = Mappers.sprite.get(e2);
            return (int)Math.signum(sp2.y - sp1.y);
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

            if (physics != null) {
                Vector2 pos = physics.body.getPosition();
                Vector2 interpolated = sprite.last.cpy().interpolate(pos, alpha, Interpolation.linear);

                float xOff = (float) (sprite.region.getRegionWidth() / 32) / 2;
                float yOff = (float) (sprite.region.getRegionHeight() / 32) / 2;

                sprite.x = (pos.x) - xOff; //interpolated.x - 16;
                sprite.y = (pos.y) - yOff; //interpolated.y - 16;
                sprite.last = pos.cpy();
            }

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
