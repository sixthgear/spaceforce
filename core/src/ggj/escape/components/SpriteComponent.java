package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.world.Level;


public class SpriteComponent extends Component {

    public TextureRegion region;
    public Animation animation;
    public float stateTime = 0.0f;
    public float x;
    public float y;
    public float w;
    public float h;
    public Vector2 last = new Vector2(0,0);
    public boolean flipped = false;
    public boolean show = true;

    public SpriteComponent(TextureRegion region) {
        this.region = region;
        this.w = region.getRegionWidth() / Level.TILESIZE;
        this.h = region.getRegionHeight() / Level.TILESIZE;
    }

    public SpriteComponent(Animation animation) {
        this.animation = animation;
        this.region = animation.getKeyFrame(0.0f);
        this.w = region.getRegionWidth() / Level.TILESIZE;
        this.h = region.getRegionHeight() / Level.TILESIZE;
    }
}
