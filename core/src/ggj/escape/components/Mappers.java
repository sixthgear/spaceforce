package ggj.escape.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {

    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<CharacterComponent> player = ComponentMapper.getFor(CharacterComponent.class);
    public static final ComponentMapper<BulletComponent> bullet = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper<SpiderComponent> spider = ComponentMapper.getFor(SpiderComponent.class);
    public static final ComponentMapper<BaddieComponent> enemy = ComponentMapper.getFor(BaddieComponent.class);

}
