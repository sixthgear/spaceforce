package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;
import ggj.escape.components.SpriteComponent;

public class RenderSystem extends EntitySystem {

    public static Texture tex = new Texture("sprites/placeholders.png");
    public static Texture sprites = new Texture("sprites/sprite-sheet.png");
    public static TextureRegion bullet = new TextureRegion(RenderSystem.tex, 128, 0, 32, 32);

    private ImmutableArray<Entity> entities;
    private SpriteBatch batch = new SpriteBatch();

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(SpriteComponent.class));
    }

    public void interpolate(float delta, float alpha) {

        // interpolate sprites according to frame alpha
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
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
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            SpriteComponent sprite = Mappers.sprite.get(entity);
            batch.draw(sprite.region, sprite.x, sprite.y, sprite.w, sprite.h);
        }
        batch.end();
    }

}
