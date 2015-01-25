package ggj.escape.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Family;

public class Mappers {

    public static class families {
        public static Family characters = Family.getFor(CharacterComponent.class);
        public static Family players = Family.getFor(PlayerComponent.class, CharacterComponent.class);
        public static Family baddies = Family.getFor(BaddieComponent.class, PhysicsComponent.class, SpriteComponent.class);
        public static Family physics = Family.getFor(PhysicsComponent.class);
        public static Family sprites = Family.getFor(SpriteComponent.class);
        public static Family bullets = Family.getFor(BulletComponent.class);
    }

    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<CharacterComponent> character = ComponentMapper.getFor(CharacterComponent.class);
    public static final ComponentMapper<PlayerComponent> player= ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<BulletComponent> bullet = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper<SpiderComponent> spider = ComponentMapper.getFor(SpiderComponent.class);
    public static final ComponentMapper<BaddieComponent> enemy = ComponentMapper.getFor(BaddieComponent.class);

}
