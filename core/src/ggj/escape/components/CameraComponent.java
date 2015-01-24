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

    public CameraComponent(float width, float height, Vector2 scale, Level level, ImmutableArray<Entity> targets) {

        this.camera = new OrthographicCamera(width, height);
        this.camera.setToOrtho(false, 40, 25);
        this.scale = scale;

        if (targets != null) {
            this.targets = targets;
        }

        if (level != null) {
            this.level = level;
        }

    }
}
