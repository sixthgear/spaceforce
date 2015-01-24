package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent extends Component {

    public TextureRegion region;
    public float x;
    public float y;
    public Vector2 last = new Vector2(0,0);


    public SpriteComponent(TextureRegion region) {
        this.region = region;
    }
}
