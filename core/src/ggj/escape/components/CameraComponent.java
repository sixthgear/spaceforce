package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.world.Level;

public class CameraComponent extends Component {

    public OrthographicCamera camera;
    public ImmutableArray<Entity> targets;
    public Level level;
    public Vector2 scale;

    public CameraComponent(float height, float width, Vector2 scale, Level level, ImmutableArray<Entity> targets) {

        this.camera = new OrthographicCamera(width, height);
        this.camera.setToOrtho(false, width, height);
        this.scale = scale;

        if (targets != null) {
            this.targets = targets;
            Vector2 avg = new Vector2();
            for (int i = 0; i < targets.size(); i++) {
                avg.add(Mappers.physics.get(targets.get(i)).body.getPosition());
            }
            avg.scl(1.0f / targets.size());
            this.camera.position.set(avg, 0);
        }

        if (level != null) {
            this.level = level;
        }

    }
}
