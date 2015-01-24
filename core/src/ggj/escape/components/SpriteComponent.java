package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteComponent extends Component {

    public TextureRegion region;
    public float x;
    public float y;

    public SpriteComponent(TextureRegion region) {
        this.region = region;
    }
}
