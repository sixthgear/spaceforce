package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.world.World;

public class CameraComponent extends Component {

    public OrthographicCamera camera;
    public Entity target;
    public World world;
    public Vector2 scale;

    public CameraComponent(float height, float width, Vector2 scale, World world, Entity target) {

        this.camera = new OrthographicCamera(width, height);
        this.camera.setToOrtho(false, width, height);
        this.scale = scale;

        if (target != null) {
            this.target = target;
            this.camera.position.set(Mappers.physics.get(target).pos, 0);
        }

        if (world != null) {
            this.world = world;
        }

    }
}
