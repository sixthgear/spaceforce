package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class PhysicsComponent extends Component{

    public Vector2 pos0 = new Vector2();
    public Vector2 pos = new Vector2();
    public Vector2 acc = new Vector2();
    public float damping = 0.99f;

    public BoundingBox bounds;

    public PhysicsComponent(int width, int height) {
        bounds = new BoundingBox(new Vector3(0,0,0), new Vector3(width, height, 0));
    }
}
